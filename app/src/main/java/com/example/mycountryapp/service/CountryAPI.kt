package com.example.mycountryapp.service

import com.example.mycountryapp.model.CountryModel

import io.reactivex.rxjava3.core.Single

import retrofit2.http.GET

interface CountryAPI {
    //GET
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    //BASE_URL= https://raw.githubusercontent.com/
    //EXT= atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<CountryModel>>


}