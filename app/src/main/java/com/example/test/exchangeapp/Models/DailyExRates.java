package com.example.test.exchangeapp.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "DailyExRates", strict = false)
public class DailyExRates {

    @Attribute(name = "Date")
    private String Date;

    @ElementList(inline = true)
    public List<Currency>  currency ;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "DailyExRates{" +
                "Date='" + Date + '\'' +
                ", currency=" + currency +
                '}';
    }
}
