package com.example.test.exchangeapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.exchangeapp.Interfaces.ItemTouchHelperAdapter;
import com.example.test.exchangeapp.Interfaces.ItemTouchHelperViewHolder;
import com.example.test.exchangeapp.Interfaces.OnStartDragListener;
import com.example.test.exchangeapp.Models.Currency;

import java.util.Collections;
import java.util.List;

public class ExchangeListAdapter extends RecyclerView.Adapter<ExchangeListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    List<Currency> mCurrencyList;
    private final OnStartDragListener mDragStartListener;
    OnItemClickListener mItemClickListener;

    public ExchangeListAdapter(Context mContext, List<Currency> mCurrencyList, OnStartDragListener dragListner){
        this.mContext = mContext;
        this.mCurrencyList = mCurrencyList;
        mDragStartListener = dragListner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exchange_item, parent, false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.mTvLine1.setText(mCurrencyList.get(i).getCharCode() + " " + mCurrencyList.get(i).getRate() + " BYN");
        holder.mTvLine2.setText(mCurrencyList.get(i).getName() + " за " + mCurrencyList.get(i).getScale() + " ед.");
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,ItemTouchHelperViewHolder {
        private TextView mTvLine1;
        private TextView mTvLine2;

        public ViewHolder(View view) {
            super(view);
            mTvLine1 = (TextView)view.findViewById(R.id.tv_line_1);
            mTvLine2 = (TextView)view.findViewById(R.id.tv_line_2);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        mCurrencyList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //Log.v("", "Log position" + fromPosition + " " + toPosition);
        if (fromPosition < mCurrencyList.size() && toPosition < mCurrencyList.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mCurrencyList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mCurrencyList, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    public void updateList(List<Currency> list) {
        mCurrencyList = list;
        notifyDataSetChanged();
    }

}
