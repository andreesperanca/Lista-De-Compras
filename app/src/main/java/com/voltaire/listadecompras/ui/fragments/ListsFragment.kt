package com.voltaire.listadecompras.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.database.models.MarketListWithItems
import com.voltaire.listadecompras.databinding.FragmentListsBinding
import com.voltaire.listadecompras.ui.adapters.MarketListsAdapter
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.factory.MarketListViewModelFactory
import com.voltaire.listadecompras.utils.SwipeHandler
import com.voltaire.listadecompras.utils.dialog.CreateListDialog

class ListsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarketListsAdapter
    private lateinit var binding: FragmentListsBinding

    private val viewModel: MarketListViewModel by viewModels {
        MarketListViewModelFactory((requireContext().applicationContext as ListsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = MarketListsAdapter()
        binding = FragmentListsBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OBSERVER LISTS
        viewModel.allListsWithItems.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it as MutableList)
            if (adapter.list.size == 0)
            {
                binding.emptyListLayout.visibility = View.VISIBLE
            } else
            {
                binding.emptyListLayout.visibility = View.INVISIBLE
            }
        })

        //CONFIGURE RECYCLERVIEW
        configureRecyclerView()

        //ADD BTN LISTENER
        binding.btnAddList.setOnClickListener {
            CreateListDialog().show(requireContext(), createList = { viewModel.insert(it) })
        }

        //Swipe
        val swipe = SwipeHandler(actionAfterSwipe = { adapter.removeAt(position = it)})
        swipe.builder(binding.rvHome)
    }

    private fun configureRecyclerView() {
        recyclerView = binding.rvHome
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val divisor = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
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
        val index = viewModel.allListsWithItems.value?.indexOf(list)

        val action = ListsFragmentDirections.actionListsFragmentToInnerListFragment(
            marketListWithItems = list,
            index = index ?: 0
        )
        findNavController().navigate(action)
    }
}