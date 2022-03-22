package com.voltaire.listadecompras.repository

import androidx.annotation.WorkerThread
import com.voltaire.listadecompras.database.daos.MarketListsDao
import com.voltaire.listadecompras.database.models.MarketList
import kotlinx.coroutines.flow.Flow

class MarketListsRepository (private val marketListDao: MarketListsDao) {

    val allLists: Flow<List<MarketList>> = marketListDao.getAllLists()

    suspend fun insert(list: MarketList) {
        marketListDao.insert(list)
    }

}