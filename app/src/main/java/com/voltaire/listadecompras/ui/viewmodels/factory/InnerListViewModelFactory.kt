package com.voltaire.listadecompras.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voltaire.listadecompras.ui.viewmodels.InnerListViewModel

//class InnerListViewModelFactory (private val repository: InnerListRepository): ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(InnerListViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return InnerListViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}