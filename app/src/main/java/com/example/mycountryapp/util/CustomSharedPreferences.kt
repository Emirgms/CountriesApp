package com.example.mycountryapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlinx.coroutines.InternalCoroutinesApi

import kotlinx.coroutines.internal.synchronized

class CustomSharedPreferences {

    companion object{
        private val PREFERENCES_TIME="preferences_time"
        private var sharedPreferences:SharedPreferences? =null

        @Volatile private var instance: CustomSharedPreferences?=null
        private val lock=Any()
        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context):CustomSharedPreferences=instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreference(context).also {
                instance=it
            }
        }

        private fun makeCustomSharedPreference(context: Context):CustomSharedPreferences {
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }
    fun saveTime(time:Long){
        sharedPreferences?.edit(true){
            putLong(PREFERENCES_TIME,time)
        }
    }
fun getTime()= sharedPreferences?.getLong(PREFERENCES_TIME,0)
}