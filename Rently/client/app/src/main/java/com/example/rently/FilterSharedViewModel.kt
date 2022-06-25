package com.example.rently

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.rently.ui.screens.filter.state.FilterFormState

class FilterSharedViewModel: ViewModel() {
    var state by mutableStateOf<FilterFormState?>(null)
        private set

    @JvmName("setState1")
    fun setState(newState: FilterFormState){
        state = newState
    }
}