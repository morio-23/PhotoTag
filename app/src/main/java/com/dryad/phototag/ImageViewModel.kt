package com.dryad.phototag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import java.lang.IllegalArgumentException

class ImageViewModel(private val dao: DataBaseDao): ViewModel() {

    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        ImagePagingSource(dao)
    }.flow.cachedIn(viewModelScope)
}

class ImageViewModelFactory(private val dao: DataBaseDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            @Suppress("UNCEHCKED_CAST")
            return ImageViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}