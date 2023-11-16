package com.example.mycountryapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mycountryapp.model.CountryModel

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries:CountryModel):List<Long>

    @Query("Select * From Country")
    suspend fun getAllCountries():List<CountryModel>

    @Query("Select * From Country Where uuid= :countryId")
    suspend fun getCountryById(countryId:Int):CountryModel

    @Query("Delete From Country")
    suspend fun  deleteAllCountries()
}