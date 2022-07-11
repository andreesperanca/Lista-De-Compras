package com.voltaire.listadecompras.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.ActivityMainBinding
import com.voltaire.listadecompras.ui.adapters.MarketListsAdapter
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.factory.MarketListViewModelFactory
import com.voltaire.listadecompras.utils.dialog.CreateListDialog


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarketListsAdapter
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MarketListViewModel by viewModels {
        MarketListViewModelFactory((application as ListsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.allListsWithItems.observe(this, Observer {
            adapter.updateList(it as MutableList)
            if (adapter.list.size == 0) {
                binding.emptyListLayout.visibility = View.VISIBLE
            } else {
                binding.emptyListLayout.visibility = View.INVISIBLE
            }
        })

        configureRecyclerView()

        binding.btnAddList.setOnClickListener {
            CreateListDialog(this, createList = {
                viewModel.insert(it)
            }).show()
        }

        //SWIPE HANDLER
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.START or ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvHome)

    }

    private fun configureRecyclerView() {

        val divisor = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        this.recyclerView = this.binding.rvHome
        this.adapter = MarketListsAdapter()
        this.recyclerView.adapter = this.adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerView.addItemDecoration(divisor)

        adapter.excludeList = { list ->
            excludeList(list)
        }
        adapter.openList = { list ->
            openList(list)
        }
    }

    private fun excludeList(list: MarketListWithItems) {
        viewModel.deleteList(list.marketList)
        viewModel.deletelistItems(list.itemsLists)
    }

    private fun openList(list: MarketListWithItems) {

        val intent = Intent(this, InnerListActivity::class.java)
        val index = viewModel.allListsWithItems.value!!.indexOf(list)

        intent.putExtra("listMarketListWithItems", list)
        intent.putExtra("index", index)
        startActivity(intent)

    }
}