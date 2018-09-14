package com.example.test.exchangeapp.UI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.exchangeapp.EditItemTouchHelperCallback;
import com.example.test.exchangeapp.ExchangeListAdapter;
import com.example.test.exchangeapp.Interfaces.ExchangeService;
import com.example.test.exchangeapp.Interfaces.OnStartDragListener;
import com.example.test.exchangeapp.Models.DailyExRates;
import com.example.test.exchangeapp.Net.Response;
import com.example.test.exchangeapp.R;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ExchangeListFragment extends Fragment implements OnStartDragListener {

    private static final String TAG = ExchangeListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ExchangeListAdapter mExchangeListAdapter;
    ItemTouchHelper mItemTouchHelper;
    Response mResponse = new Response();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.nbrb.by/Services/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    private ExchangeService service = retrofit.create(ExchangeService.class);

    public static ExchangeListFragment createInstance(FragmentManager fragmentManager) {
        ExchangeListFragment myFragment = (ExchangeListFragment) fragmentManager.findFragmentByTag(ExchangeListFragment.TAG);
        if (myFragment == null) {
            myFragment = new ExchangeListFragment();
        }
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_exchange_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (isOnline()){
            initView();
        } else {
            Toast.makeText(getActivity(),"Нет подключения к интернету.",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void initView () {

        DailyExRates dailyExRates = mResponse.callExch(getActivity());

        if (dailyExRates != null){
            if (dailyExRates.getCurrency().size() != 0) {
                mExchangeListAdapter = new ExchangeListAdapter(getContext(),dailyExRates.getCurrency(), this);
                ItemTouchHelper.Callback callback =
                        new EditItemTouchHelperCallback(mExchangeListAdapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(mRecyclerView);
                mRecyclerView.setAdapter(mExchangeListAdapter);
            } else {
                Toast.makeText(getActivity(),"Данные ответа не корректны.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setHasOptionsMenu(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
