package com.voltaire.listadecompras.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.database.models.MarketList
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
            adapter.updateList(it)
        })

        configureRecyclerView()

        binding.btnAddList.setOnClickListener {
            CreateListDialog(this, createList = {
                viewModel.insert(it)
            }).show()
        }
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