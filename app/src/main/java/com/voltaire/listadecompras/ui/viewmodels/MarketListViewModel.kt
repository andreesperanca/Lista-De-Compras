package com.voltaire.listadecompras.ui.viewmodels

import androidx.lifecycle.*
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.repository.MarketListsRepository
import kotlinx.coroutines.launch

class MarketListViewModel (private val repository: MarketListsRepository) : ViewModel() {

    val allLists: LiveData<List<MarketList>> = repository.allLists.asLiveData()

    fun insert (list : MarketList) = viewModelScope.launch {
        repository.insert(list)
    }

}

class WordViewModelFactory(private val repository: MarketListsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}