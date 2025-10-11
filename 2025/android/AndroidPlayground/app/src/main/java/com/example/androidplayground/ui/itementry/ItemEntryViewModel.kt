package com.example.androidplayground.ui.itementry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidplayground.data.Item
import com.example.androidplayground.data.ItemRepository

class ItemEntryViewModel(
    private val itemRepository: ItemRepository
) : ViewModel() {
    var itemDetailsUiState by mutableStateOf(ItemDetails())
        private set

    fun updateItemDetailsState(itemDetails: ItemDetails) {
        itemDetailsUiState = itemDetails
    }

    suspend fun saveItem() {
        itemRepository.insert(itemDetailsUiState.toItem())
    }
}

data class ItemDetails(
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

fun ItemDetails.toItem(): Item = Item(
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)