package com.voltaire.listadecompras.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.database.models.MarketListWithItems

class SwipeHandler(
    var actionAfterSwipe: (position : Int) -> Unit = {}
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        actionAfterSwipe(viewHolder.adapterPosition)
    }

    fun builder (rv : RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(rv)
    }
}