package com.voltaire.listadecompras.di.modules

import androidx.room.Room
import com.voltaire.listadecompras.database.MarketListRoomDataBase
import com.voltaire.listadecompras.database.daos.MarketListsDao
import com.voltaire.listadecompras.repository.MarketListsRepository
import com.voltaire.listadecompras.ui.viewmodels.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single <MarketListRoomDataBase> {
        Room.databaseBuilder(
        get(),
        MarketListRoomDataBase::class.java,
        "marketList_database"
    ).build() }

    single<MarketListsDao> {
        get<MarketListRoomDataBase>().marketListDao()
    }

    single<MarketListsRepository> {
        MarketListsRepository(get())
    }

    viewModel<SharedViewModel> {
        SharedViewModel(repository = get())
    }


}