package com.example.rently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rently.repository.Repository

class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository = repository) as T
    }
}