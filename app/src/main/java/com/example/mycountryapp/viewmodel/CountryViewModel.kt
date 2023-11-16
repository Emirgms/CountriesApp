package com.example.mycountryapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycountryapp.model.CountryModel
import com.example.mycountryapp.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application):BaseViewModel(application) {
    val countryLiveData=MutableLiveData<CountryModel>()

    fun getDataFromRoom(uuid:Int){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
          val country=  dao.getCountryById(uuid)
            countryLiveData.value=country
        }
    }
}