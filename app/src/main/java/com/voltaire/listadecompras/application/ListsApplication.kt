package com.voltaire.listadecompras.application

import android.app.Application
import com.voltaire.listadecompras.database.MarketListRoomDataBase
import com.voltaire.listadecompras.repository.MarketListsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ListsApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { MarketListRoomDataBase.getDatabase(applicationScope,this) }
    val repository by lazy { MarketListsRepository(database.marketListDao()) }

}