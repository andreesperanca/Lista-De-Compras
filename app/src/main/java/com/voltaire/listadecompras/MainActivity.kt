package com.voltaire.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voltaire.listadecompras.application.ListsApplication
import com.voltaire.listadecompras.databinding.ActivityMainBinding
import com.voltaire.listadecompras.ui.adapters.MarketListAdapter
import com.voltaire.listadecompras.ui.viewmodels.MarketListViewModel
import com.voltaire.listadecompras.ui.viewmodels.WordViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarketListAdapter
    private lateinit var binding: ActivityMainBinding

    private val wordViewModel : MarketListViewModel by viewModels {
        WordViewModelFactory((application as ListsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.recyclerView = this.binding.recyclerMain
        this.adapter = MarketListAdapter()
        this.recyclerView.adapter = this.adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()

        wordViewModel.allLists.observe(this, Observer {
            it.let { adapter.submitList(it) }
        })

    }
}