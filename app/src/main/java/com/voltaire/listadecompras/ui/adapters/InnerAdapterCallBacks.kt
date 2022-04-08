package com.voltaire.listadecompras.ui.adapters

import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketListWithItems

interface InnerAdapterCallBacks {

    fun onItemDelete ( item : Item)
    fun onItemIGot ( item : Item )
}