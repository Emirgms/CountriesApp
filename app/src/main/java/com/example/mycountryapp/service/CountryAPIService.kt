package com.example.mycountryapp.service

import com.example.mycountryapp.model.CountryModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CountryAPIService {
    private val BASE_URL="https://raw.githubusercontent.com/"

private val api=Retrofit.Builder().baseUrl(BASE_URL)
   .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .build()
    .create(CountryAPI::class.java)

    fun getData(): Single<List<CountryModel>> {
        return  api.getCountries()
    }

}