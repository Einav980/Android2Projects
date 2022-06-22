package com.example.rently.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.rently.Resource
import com.example.rently.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScoped
class DatastorePreferenceRepository @Inject constructor(@ApplicationContext private val context: Context) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

    companion object{
        val LOGGED_IN = booleanPreferencesKey(name = "LOGGED_IN")
        val USER_EMAIL= stringPreferencesKey("user_email")
    }

    suspend fun setLoggedIn(email : String){
        context.dataStore.edit { settings ->
            settings[LOGGED_IN] = true
            settings[USER_EMAIL] = email
        }
    }

    suspend fun setLoggedOut(){
        context.dataStore.edit { settings ->
            settings[LOGGED_IN] = false
            settings[USER_EMAIL] = ""
        }
    }

    suspend fun isLoggedIn(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[LOGGED_IN] ?: false
    }

        suspend fun checkIfUserIsLoggedIn(): Resource<Boolean?> {
        try{
            val preferences = context.dataStore.data.first()
            return Resource.Success(preferences[LOGGED_IN])
        }
        catch (e: java.lang.Exception){
            return Resource.Error("Failed retrieving user state", false)
        }
    }

    fun getUserEmail(): Flow<String> = context.dataStore.data
        .map { preferences -> preferences[USER_EMAIL] ?: ""
        }
}