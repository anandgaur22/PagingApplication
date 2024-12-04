package com.android.pagingapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class ItemViewModel : ViewModel() {
    private val repository = ItemRepository()

    val items: Flow<PagingData<String>> = repository.getItems().cachedIn(viewModelScope)
}