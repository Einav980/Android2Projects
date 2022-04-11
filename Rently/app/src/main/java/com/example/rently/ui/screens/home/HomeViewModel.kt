package com.example.rently.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userAuth: UserRepository
): ViewModel(){

}