package com.example.mycountryapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import com.example.mycountryapp.model.CountryModel
import com.example.mycountryapp.service.CountryAPIService
import com.example.mycountryapp.service.CountryDatabase
import com.example.mycountryapp.util.CustomSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application) {
    private val countryAPIService=CountryAPIService()
    private val disposable=CompositeDisposable()
    private var customPreferences=CustomSharedPreferences(getApplication())
    private var refresTime=10*60*1000*1000*1000L

    val countries=MutableLiveData<List<CountryModel>>()
    var countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime=customPreferences.getTime()
        if(updateTime !=null&&updateTime !=0L && System.nanoTime()-updateTime<refresTime) {
            getDataFromSQLite()

        } else {
            getDataFromApi()
        }

    }
     fun refreshFromAPI(){
        getDataFromApi()
    }
    private fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)

        }
    }
    private fun getDataFromApi(){
    countryLoading.value=true

     disposable.add(
         countryAPIService.getData()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeWith(object:DisposableSingleObserver<List<CountryModel>>(){
                 override fun onSuccess(t: List<CountryModel>) {
                    storeInSQLite(t)

                 }

                 override fun onError(e: Throwable) {
                    countryLoading.value=false
                     countryError.value=true
                     e.printStackTrace()
                 }

                 })
     )

    }
    private fun showCountries(countryList:List<CountryModel>){
        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }
    private fun storeInSQLite(list:List<CountryModel>){
    launch {
        val dao=CountryDatabase(getApplication()).countryDao()
        dao.deleteAllCountries()
       val listLong= dao.insertAll(*list.toTypedArray()) //list -> list,individual
        var i=0
        while (i<list.size) {
            list[i].uuid=listLong[i].toInt()
            i += 1
        }
        showCountries(list)
    }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}