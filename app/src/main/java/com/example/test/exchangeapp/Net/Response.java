package com.example.test.exchangeapp.Net;

import android.content.Context;
import android.widget.Toast;

import com.example.test.exchangeapp.Interfaces.ExchangeService;
import com.example.test.exchangeapp.Models.DailyExRates;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Response {

    public DailyExRates callExch(Context context) {
        DailyExRates dailyExRates = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nbrb.by/Services/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        ExchangeService service = retrofit.create(ExchangeService.class);
        try {
            Call<DailyExRates> call = service.callExchange();
            retrofit2.Response<DailyExRates> response = null;
            response = call.execute();
            dailyExRates = response.body();
            return dailyExRates;
        } catch (IOException e) {
            Toast.makeText(context,"Ошибка сервера.",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return dailyExRates;
        }
    }
}
