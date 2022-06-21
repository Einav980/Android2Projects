package com.example.rently

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.rently.model.Apartment
import com.example.rently.validation.presentation.FilterFormEvent
import com.example.rently.validation.use_case.FilterFormState

class FilterSharedViewModel: ViewModel() {
    var state by mutableStateOf<FilterFormState?>(null)
        private set

    @JvmName("setState1")
    fun setState(newState: FilterFormState){
        state = newState
    }
}