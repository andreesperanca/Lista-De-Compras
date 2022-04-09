package com.voltaire.listadecompras.ui.viewmodels

import androidx.lifecycle.*
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.repository.MarketListsRepository
import kotlinx.coroutines.launch

class MarketListViewModel (private val repository: MarketListsRepository) : ViewModel() {

    val allListsWithItems : LiveData<List<MarketListWithItems>> = repository.allListsWithItems.asLiveData()

    fun insert (list : MarketList) = viewModelScope.launch {
        repository.insert(list)
    }

    fun deleteList (list: MarketList) = viewModelScope.launch {
        repository.deleteList(list)
    }

    fun insertItem ( item : Item) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun deleteItem ( item : Item ) = viewModelScope.launch {
        repository.deleteItem(item)
    }
    fun deletelistItems ( listItems : List<Item>) = viewModelScope.launch {
        repository.deleteItemList(listItems)
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