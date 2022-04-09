package com.voltaire.listadecompras.repository

import com.voltaire.listadecompras.database.daos.MarketListsDao
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.database.models.MarketListWithItems
import kotlinx.coroutines.flow.Flow

class MarketListsRepository (private val marketListDao: MarketListsDao) {

    val allListsWithItems : Flow<List<MarketListWithItems>> = marketListDao.getListsWithItems()

    suspend fun insert(list: MarketList) {
        marketListDao.insert(list)
    }

    suspend fun deleteList (list : MarketList) {
        marketListDao.deleteList(list)
    }

    suspend fun insertItem (item : Item) {
        marketListDao.insertItem(item)
    }

    suspend fun deleteItem (item : Item) {
        marketListDao.deleteItem(item)
    }

    suspend fun deleteItemList (listItems : List<Item>) {
        marketListDao.deleteItemList(listItems)
    }
}