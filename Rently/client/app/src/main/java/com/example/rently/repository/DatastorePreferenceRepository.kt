package com.example.rently.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.rently.util.Constants
import kotlinx.coroutines.flow.first

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

class DatastorePreferenceRepository(private val context: Context) {

    companion object{
        val LOGGED_IN = booleanPreferencesKey(name = "LOGGED_IN")
    }

    suspend fun setLoggedIn(){
        context.datastore.edit { settings ->
            settings[LOGGED_IN] = true
        }
    }

    suspend fun setLoggedOut(){
        context.datastore.edit { settings ->
            settings[LOGGED_IN] = false
        }
    }

    suspend fun isLoggedIn(): Boolean {
        val dataStoreKey = booleanPreferencesKey("LOGGED_IN")
        val preferences = context.datastore.data.first()
        return preferences[dataStoreKey] ?: false
    }
}