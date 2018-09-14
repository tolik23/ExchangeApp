package com.example.test.exchangeapp.Interfaces;

import com.example.test.exchangeapp.Models.DailyExRates;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExchangeService {

    @GET("XmlExRates.aspx")
    Call<DailyExRates> callExchange();


}
