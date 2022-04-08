package com.voltaire.listadecompras.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.ActivityMainBinding
import com.voltaire.listadecompras.ui.adapters.MainAdapterCallBacks
import com.voltaire.listadecompras.ui.adapters.MarketListsAdapter
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.WordViewModelFactory


class MainActivity : AppCompatActivity(), MainAdapterCallBacks {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarketListsAdapter
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MarketListViewModel by viewModels {
        WordViewModelFactory((application as ListsApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        viewModel.allListsWithItems.observe(this, Observer {
            adapter.setLists(it)
        })


        this.recyclerView = this.binding.rvHome
        this.adapter = MarketListsAdapter(this)
        this.recyclerView.adapter = this.adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnAddList.setOnClickListener {
            val view = View.inflate(this, R.layout.dialog_add_list, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            view.findViewById<Button>(R.id.btn_dialog_add).setOnClickListener {
                val txtAdd = view.findViewById<EditText>(R.id.txt_dialog_add)
                if (txtAdd.text != null) {
                    viewModel.insert(MarketList(0,txtAdd.text.toString()))
                    dialog.dismiss()
                }
            }
        }

    }

    override fun onItemClickListenerExclude(list: MarketListWithItems) {
        viewModel.deleteList(list.marketList)
        viewModel.deletelistItems(list.itemsLists)
    }

    override fun onItemClickListenerOpen(list: MarketListWithItems) {

        var intent = Intent(this, InnerListActivity::class.java)

        var index = viewModel.allListsWithItems.value!!.indexOf(list)

        intent.putExtra("listMarketListWithItems", list)
        intent.putExtra("index", index)
        startActivity(intent)

    }
}