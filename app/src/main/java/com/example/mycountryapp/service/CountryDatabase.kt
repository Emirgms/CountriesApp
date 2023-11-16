package com.example.mycountryapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.mycountryapp.model.CountryModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [CountryModel::class], version = 1)
abstract class CountryDatabase:RoomDatabase() {

    abstract fun countryDao():CountryDao

    companion object {
        @Volatile private var instance :CountryDatabase?=null

        private val lock = Any()
        @OptIn(InternalCoroutinesApi::class)
        operator  fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance=it
            }
        }

       private fun createDatabase(context: Context)= databaseBuilder(context.applicationContext,CountryDatabase::class.java,"countrydatabase").build()
    }
}