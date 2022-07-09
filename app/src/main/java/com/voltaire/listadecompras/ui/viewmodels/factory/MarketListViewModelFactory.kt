package com.voltaire.listadecompras.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voltaire.listadecompras.repository.MarketListsRepository
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel

class MarketListViewModelFactory (private val repository: MarketListsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}