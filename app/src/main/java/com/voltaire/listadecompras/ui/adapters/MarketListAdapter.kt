package com.voltaire.listadecompras.ui.adapters


import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.RecyclerViewListsBinding

class MarketListsAdapter(
    var excludeList: (list : MarketListWithItems) -> Unit = {},
    var openList: (list : MarketListWithItems) -> Unit = {},
) : RecyclerView.Adapter<MarketListsAdapter.ListViewHolder> () {

    private var list = emptyList<MarketListWithItems>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RecyclerViewListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(lists: List<MarketListWithItems>) {
        list = lists
        notifyDataSetChanged()
    }

    inner class ListViewHolder(
       private val binding: RecyclerViewListsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val nameList: TextView = binding.listNameString
        private val btnExclude: Button = binding.btnExclude
        private val btnOpenList: TextView = binding.btnOpenList

        fun bind(relationList: MarketListWithItems) {
            nameList.text = relationList.marketList.name

            btnExclude.setOnClickListener {
                val listSelected = (list[adapterPosition])
                excludeList(listSelected)
            }
            btnOpenList.setOnClickListener {
                val listSelected = (list[adapterPosition])
                openList(listSelected)
            }

        }
    }
}