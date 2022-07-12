package com.voltaire.listadecompras.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.database.models.Item
import androidx.appcompat.app.AppCompatActivity
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.RecyclerViewItemsBinding
import com.voltaire.listadecompras.ui.viewmodels.InnerListViewModel

class InnerListAdapter(
    var excludeItem: (item : Item) -> Unit = {},
    var selectedItem: (item : Item) -> Unit = {}
    ) : RecyclerView.Adapter<InnerListAdapter.InnerListViewHolder>() {

    private var itemsList: List<Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerListViewHolder {
        val binding =
            RecyclerViewItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerListViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setItems(itemLists: List<Item>) {
        itemsList = itemLists
        notifyDataSetChanged()
    }

    inner class InnerListViewHolder(private val binding: RecyclerViewItemsBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        private var txtNameList: TextView = binding.itemName
        private var txtPrice: TextView = binding.txtPrice
        private var txtAmount: TextView = binding.txtAmount

        fun bind(marketListWithItems: Item) {

            txtNameList.text = marketListWithItems.itemName
            txtPrice.text = marketListWithItems.price
            txtAmount.text = marketListWithItems.amount

//            btnIGotItem.setOnClickListener {
//                it.isEnabled = false
//            }
//            btnDeleteItem.setOnClickListener {
//                val itemSelected = (itemsList[adapterPosition])
//                excludeItem(itemSelected)
//            }
        }
    }
}