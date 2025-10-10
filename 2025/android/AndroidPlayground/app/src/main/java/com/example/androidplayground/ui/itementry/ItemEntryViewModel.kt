package com.example.androidplayground.ui.itementry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidplayground.data.ItemRepository

class ItemEntryViewModel(
    private val itemRepository: ItemRepository
) : ViewModel() {
    var itemDetailsState by mutableStateOf(ItemDetails())
        private set

    fun updateItemDetailsState(itemDetails: ItemDetails) {
        itemDetailsState = itemDetails
    }
}

data class ItemDetails(
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)