package com.voltaire.listadecompras.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.voltaire.listadecompras.databinding.ActivityListingListBinding

class ListListing : AppCompatActivity() {


    private lateinit var binding: ActivityListingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityListingListBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
    }
}