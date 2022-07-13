package com.voltaire.listadecompras.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.databinding.FragmentInnerListBinding
import com.voltaire.listadecompras.ui.adapters.InnerListAdapter
import com.voltaire.listadecompras.ui.viewmodels.SharedViewModel
import com.voltaire.listadecompras.utils.Constants
import com.voltaire.listadecompras.utils.SwipeHandler
import com.voltaire.listadecompras.utils.dialog.CreateItemDialog
import com.voltaire.listadecompras.utils.functions.toastCreator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InnerListFragment : Fragment() {

    private lateinit var binding: FragmentInnerListBinding
    private lateinit var adapter: InnerListAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: SharedViewModel by sharedViewModel()
    private val args: InnerListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = InnerListAdapter()
        binding = FragmentInnerListBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OBSERVER
        val index = args.index
        viewModel.allListsWithItems.observe(viewLifecycleOwner, Observer { listWithItems ->
            if (listWithItems != null)
            {
                val listAtt: List<Item> = listWithItems[index].itemsLists
                adapter.updateItems(listAtt)
                viewModel.calculatePriceCart(listAtt)
                binding.cartPrice.text = viewModel.updatePriceCart().toString()
            } else
            {
                toastCreator(requireContext(), Constants.ERROR_MESSAGE)
            }
        })

        // TOOLBAR CONFIG
        binding.toolbar.title = args.marketListWithItems.marketList.name
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_innerListFragment_to_listsFragment)
        }

        //GET PARCELABLE
        val listMarketListWithItems = args.marketListWithItems

        //SWIPE HANDLER
        SwipeHandler(actionAfterSwipe = { adapter.removeAt(position = it) })
            .builder(binding.rvInnerList)

        //ADD ITEM BTN
        binding.btnAddItem.setOnClickListener {
            CreateItemDialog(
                requireContext(),
                idList = listMarketListWithItems?.marketList?.idList ?: 0,
                createItem = { item ->
                    viewModel.insertItem(item)
                }).show()
        }

        //CONFIG RECYCLERVIEW
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
            val divisor = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            adapter = InnerListAdapter()
            recyclerView = binding.rvInnerList
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recyclerView.addItemDecoration(divisor)

            adapter.excludeItem = { item ->
                viewModel.deleteItem(item)
        }
    }
}