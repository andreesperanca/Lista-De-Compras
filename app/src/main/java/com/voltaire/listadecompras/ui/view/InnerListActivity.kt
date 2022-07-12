package com.voltaire.listadecompras.ui.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.ActivityInnerListBinding
import com.voltaire.listadecompras.ui.adapters.InnerListAdapter
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.factory.MarketListViewModelFactory
import com.voltaire.listadecompras.utils.Constants.Companion.ERROR_MESSAGE
import com.voltaire.listadecompras.utils.dialog.CreateItemDialog
import com.voltaire.listadecompras.utils.functions.toastCreator

class InnerListActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityInnerListBinding
    private lateinit var adapter: InnerListAdapter
    private lateinit var recyclerView: RecyclerView
    private var priceTotal: Double = 0.0

    private val viewModelInner: MarketListViewModel by viewModels {
        MarketListViewModelFactory((application as ListsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInnerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //GET PARCELABLE
        val listMarketListWithItems =
            intent.getParcelableExtra<MarketListWithItems>("listMarketListWithItems")
        val index = intent.getIntExtra("index", 0)

        // ACTION BAR NAME
        setSupportActionBar(binding.toolbar)

        supportActionBar?.let { toolbar ->
            toolbar.setDisplayHomeAsUpEnabled(true)
            toolbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
            toolbar.title = listMarketListWithItems?.marketList?.name
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
        itemTouchHelper.attachToRecyclerView(binding.rvInnerList)

        //CONFIG RECYCLERVIEW
        configureRecyclerView()

        //OBSERVER
        viewModelInner.allListsWithItems.observe(this, Observer { it: List<MarketListWithItems>? ->
            var listAtt: List<Item> = emptyList<Item>()
            if (it != null) {
                listAtt = it[index].itemsLists
                adapter.setItems(listAtt)
                priceTotal = 0.0
                if (listAtt.isNotEmpty()) {
                    for (element in listAtt) {
                        priceTotal += element.priceTotalItem
                    }
                }
            } else {
                toastCreator(this, ERROR_MESSAGE)
            }
            binding.cartPrice.text = priceTotal.toString()
        })

        binding.btnAddItem.setOnClickListener {
                CreateItemDialog(
                    this,
                    idList = listMarketListWithItems?.marketList?.idList ?: 0,
                    createItem = { item ->
                        viewModelInner.insertItem(item)
                }).show()
            }
    }

    private fun configureRecyclerView() {
        val divisor = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        adapter = InnerListAdapter()
        recyclerView = binding.rvInnerList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerView.addItemDecoration(divisor)

        adapter.excludeItem = { item ->
            viewModelInner.deleteItem(item)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}