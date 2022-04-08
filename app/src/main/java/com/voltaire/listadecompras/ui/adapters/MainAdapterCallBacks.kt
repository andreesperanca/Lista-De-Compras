package com.voltaire.listadecompras.ui.adapters

import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.database.models.MarketListWithItems

interface MainAdapterCallBacks {

    fun onItemClickListenerExclude(list : MarketListWithItems)
    fun onItemClickListenerOpen(list : MarketListWithItems)

}

