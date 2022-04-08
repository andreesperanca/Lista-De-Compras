package com.voltaire.listadecompras.ui.adapters


import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.database.models.MarketListWithItems

class MarketListsAdapter(private val listener : MainAdapterCallBacks) : RecyclerView.Adapter<MarketListsAdapter.ListViewHolder> () {

    private var list = emptyList<MarketListWithItems>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_lists, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setLists(lists: List<MarketListWithItems>) {
        list = lists
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View,
    private val listener : MainAdapterCallBacks
    ) : RecyclerView.ViewHolder(itemView) {

        private var nameList: TextView = itemView.findViewById(R.id.list_name_string)
        private var btnExclude: Button = itemView.findViewById(R.id.btn_exclude)
        private var btnOpenList: TextView = itemView.findViewById(R.id.btn_open_list)


        fun bind(relationList: MarketListWithItems) {
            nameList.text = relationList.marketList.name

            btnExclude.setOnClickListener {
                val listSelected = (list[adapterPosition])
                listener.onItemClickListenerExclude(listSelected)
            }

            btnOpenList.setOnClickListener {
                listener.onItemClickListenerOpen(list[adapterPosition])
            }

        }
    }
}