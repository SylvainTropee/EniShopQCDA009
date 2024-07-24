package com.example.eni_shop.services

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreManager {

    val Context.datastore by preferencesDataStore(name = "user_settings")

    val DARK_MODE_KEY = booleanPreferencesKey("DARK_MODE_KEY")


    fun isDarkModeActivated(context: Context): Flow<Boolean> {
        return context.datastore.data.map {pref->
            pref[DARK_MODE_KEY] ?: false
        }
    }

    suspend fun setDarkMode(context: Context, isActivated : Boolean){
        context.datastore.edit {pref->
            pref[DARK_MODE_KEY] = isActivated
        }
    }


}