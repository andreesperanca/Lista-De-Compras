package com.voltaire.listadecompras.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.WordViewModelFactory
import org.w3c.dom.Text
import java.lang.Float.sum

class InnerListActivity : AppCompatActivity(), InnerAdapterCallBacks {

    private lateinit var binding: ActivityInnerListBinding
    private lateinit var adapter: InnerListAdapter
    private lateinit var recyclerView: RecyclerView
    private var priceTotal: Double = 0.0

    private val viewModelInner: MarketListViewModel by viewModels {
        WordViewModelFactory((application as ListsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInnerListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var listMarketListWithItems =
            intent.getParcelableExtra<MarketListWithItems>("listMarketListWithItems")
        var index = intent.getIntExtra("index", 0)

        binding.listName.text = listMarketListWithItems?.marketList?.name

        viewModelInner.allListsWithItems.observe(this, Observer { it: List<MarketListWithItems>? ->
            var listAtt: List<Item> = it!![index!!.toInt()].itemsLists
            adapter.setItems(listAtt)

            priceTotal = 0.0
            if (!listAtt.isEmpty()) {
                for (element in listAtt) {
                    priceTotal += element.priceTotalItem.toDouble()
                }
            }
            binding.cartPrice.text = getString(R.string.cartprice, priceTotal.toString())
        })

        adapter = InnerListAdapter(listMarketListWithItems!!.itemsLists, this)
        recyclerView = binding.rvInnerList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)

        binding.btnAddItem.setOnClickListener {
            val view = View.inflate(this, R.layout.dialog_add_item, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            view.findViewById<Button>(R.id.btn_dialog_add).setOnClickListener {

                val txtNameList = view.findViewById<EditText>(R.id.txt_dialog_add)
                val txtAmount = view.findViewById<EditText>(R.id.txt_amount)
                val txtPrice = view.findViewById<EditText>(R.id.txt_price)

                if (txtNameList.text != null && txtNameList.text.isNotEmpty()) {
                    viewModelInner.insertItem(
                        Item(
                            0,
                            listMarketListWithItems.marketList.idList,
                            txtNameList.text.toString(),
                            txtPrice.text.toString(),
                            txtAmount.text.toString()
                        )
                    )
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Preencha o nome do produto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemDelete(item: Item) {
        viewModelInner.deleteItem(item)
    }

    override fun onItemIGot(item: Item) {
    }

}