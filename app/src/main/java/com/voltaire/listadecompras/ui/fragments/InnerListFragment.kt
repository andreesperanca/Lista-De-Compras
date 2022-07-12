package com.voltaire.listadecompras.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.ActivityInnerListBinding
import com.voltaire.listadecompras.databinding.FragmentInnerListBinding
import com.voltaire.listadecompras.ui.adapters.InnerListAdapter
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.factory.MarketListViewModelFactory
import com.voltaire.listadecompras.utils.Constants
import com.voltaire.listadecompras.utils.dialog.CreateItemDialog
import com.voltaire.listadecompras.utils.extension.inflate
import com.voltaire.listadecompras.utils.functions.toastCreator

class InnerListFragment : Fragment() {

    private lateinit var binding: FragmentInnerListBinding
    private lateinit var adapter: InnerListAdapter
    private lateinit var recyclerView: RecyclerView
    private var priceTotal: Double = 0.0

    private val args: InnerListFragmentArgs by navArgs()

    private val viewModelInner: MarketListViewModel by viewModels {
        MarketListViewModelFactory((requireContext().applicationContext as ListsApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInnerListBinding.inflate(inflater)

        //GET PARCELABLE
        val listMarketListWithItems = args.marketListWithItems

        // TOOLBAR CONFIG
        binding.toolbar.title = args.marketListWithItems.marketList.name
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_innerListFragment_to_listsFragment)
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

        //ADD ITEM BTN
        binding.btnAddItem.setOnClickListener {
            CreateItemDialog(
                requireContext(),
                idList = listMarketListWithItems?.marketList?.idList ?: 0,
                createItem = { item ->
                    viewModelInner.insertItem(item)
                }).show()
        }


        //CONFIG RECYCLERVIEW
        configureRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //OBSERVER
        val index = args.index
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
                toastCreator(requireContext(), Constants.ERROR_MESSAGE)
            }
            binding.cartPrice.text = priceTotal.toString()
        })
    }

    private fun configureRecyclerView() {
            val divisor = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            adapter = InnerListAdapter()
            recyclerView = binding.rvInnerList
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recyclerView.addItemDecoration(divisor)

            adapter.excludeItem = { item ->
                viewModelInner.deleteItem(item)
        }
    }
}