package com.example.rently.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.rently.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

class DatastorePreferenceRepository(private val context: Context) {

    companion object{
        val LOGGED_IN = booleanPreferencesKey(name = "LOGGED_IN")
        val USER_EMAIL= stringPreferencesKey("user_email")
    }

    suspend fun setLoggedIn(email : String){
        context.datastore.edit { settings ->
            settings[LOGGED_IN] = true
            settings[USER_EMAIL] = email
        }
    }

    suspend fun setLoggedOut(){
        context.datastore.edit { settings ->
            settings[LOGGED_IN] = false
            settings[USER_EMAIL] = ""
        }
    }

    suspend fun isLoggedIn(): Boolean {
        val preferences = context.datastore.data.first()
        return preferences[LOGGED_IN] ?: false
    }

    fun getUserEmail(): Flow<String> = context.datastore.data
        .map { preferences -> preferences[USER_EMAIL] ?: ""
        }
}