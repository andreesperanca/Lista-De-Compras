package com.voltaire.listadecompras.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.ActivityInnerListBinding
import com.voltaire.listadecompras.ui.adapters.InnerAdapterCallBacks
import com.voltaire.listadecompras.ui.adapters.InnerListAdapter
import com.voltaire.listadecompras.ui.adapters.MainAdapterCallBacks
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.WordViewModelFactory

class InnerListActivity : AppCompatActivity(), InnerAdapterCallBacks {

    private lateinit var binding : ActivityInnerListBinding
    private lateinit var adapter : InnerListAdapter
    private lateinit var recyclerView : RecyclerView

    private val viewModelInner: MarketListViewModel by viewModels {
        WordViewModelFactory((application as ListsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInnerListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var listMarketListWithItems = intent.getParcelableExtra<MarketListWithItems>("listMarketListWithItems")
        var index = intent.getIntExtra("index",0)



        viewModelInner.allListsWithItems.observe(this, Observer { it: List<MarketListWithItems>? ->
            var listAtt : List<Item> = it!![index!!.toInt()].itemsLists
            adapter.setItems(listAtt)
        })


        adapter = InnerListAdapter(listMarketListWithItems!!.itemsLists, this)
        recyclerView = binding.rvInnerList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)


        binding.btnAddItem.setOnClickListener  {
            val view = View.inflate(this, R.layout.dialog_add_list, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            view.findViewById<Button>(R.id.btn_dialog_add).setOnClickListener {
                val txtAdd = view.findViewById<EditText>(R.id.txt_dialog_add)
                if (txtAdd.text != null) {
                    viewModelInner.insertItem(
                        Item(0,listMarketListWithItems!!.marketList.idList,txtAdd.text.toString(),0,0))
                    dialog.dismiss()
                }
            }
        }
    }

    override fun onItemDelete(item: Item) {
        viewModelInner.deleteItem(item)
    }

    override fun onItemIGot(item: Item) {
        Toast.makeText(this, "Você pegou", Toast.LENGTH_SHORT).show()
    }
}