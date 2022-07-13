package com.voltaire.listadecompras.ui.viewmodels

import androidx.lifecycle.*
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.repository.MarketListsRepository
import kotlinx.coroutines.launch
import java.util.concurrent.RecursiveTask

class MarketListViewModel(private val repository: MarketListsRepository) : ViewModel() {

    private var _priceCart = MutableLiveData<Double>()
    public val priceCart: LiveData<Double>
        get() = _priceCart

    val allListsWithItems: LiveData<List<MarketListWithItems>>
        get() = repository.allListsWithItems.asLiveData()

    fun insert(list: MarketList) = viewModelScope.launch {
        repository.insert(list)
    }

    fun deleteList(list: MarketList) = viewModelScope.launch {
        repository.deleteList(list)
    }

    fun insertItem(item: Item) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun deleteItem(item: Item) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun deletelistItems(listItems: List<Item>) = viewModelScope.launch {
        repository.deleteItemList(listItems)
    }

    fun calculatePriceCart(listItems: List<Item>) {
        _priceCart.value = 0.0
        if (listItems.isNotEmpty())
        {
            for (item in listItems)
            {
                _priceCart.value =+ item.priceTotalItem
            }
        }
    }

    fun updatePriceCart() : Double = priceCart.value ?: 0.0

}
