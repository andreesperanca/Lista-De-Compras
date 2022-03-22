package com.voltaire.listadecompras.ui.adapters

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.database.models.MarketList
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.voltaire.listadecompras.R

class MarketListAdapter : ListAdapter<MarketList, MarketListAdapter.MarketListViewHolder>(ListComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketListViewHolder {
        return MarketListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MarketListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class MarketListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val txtNameList : TextView = itemView.findViewById(R.id.list_name)

        fun bind(name : String?) {

            txtNameList.text = name
        }

        companion object {
            fun create(parent: ViewGroup): MarketListViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_lists, parent, false)
                return MarketListViewHolder(view)
            }
        }

    }

    class ListComparator : DiffUtil.ItemCallback<MarketList>() {

        override fun areItemsTheSame(oldItem: MarketList, newItem: MarketList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarketList, newItem: MarketList): Boolean {
            return oldItem.name == newItem.name
        }
    }
}