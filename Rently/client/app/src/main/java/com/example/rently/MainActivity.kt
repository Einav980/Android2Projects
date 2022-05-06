package com.example.rently

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.rently.navigation.SetupNavGraph
import com.example.rently.repository.datastore
import com.example.rently.ui.theme.RentlyTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController


    @SuppressLint("CoroutineCreationDuringComposition")
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentlyTheme {
                navController = rememberAnimatedNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }

    private suspend fun save(key: String, value: Boolean) {
        val datastoreKey = booleanPreferencesKey("isLoggedIn")
        datastore.edit { settings ->
            settings[datastoreKey] = value
        }
    }

    private suspend fun read(key: String): Boolean? {
        val datastoreKey = booleanPreferencesKey(key)
        val preferences = datastore.data.first()
        return preferences[datastoreKey]
    }
}
