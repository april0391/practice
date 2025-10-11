package com.example.androidplayground.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidplayground.MainApplication
import com.example.androidplayground.ui.home.HomeViewModel
import com.example.androidplayground.ui.itementry.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            HomeViewModel(
                mainApplication().container.itemRepository
            )
        }

        initializer {
            ItemEntryViewModel(
                mainApplication().container.itemRepository
            )
        }
    }
}

fun CreationExtras.mainApplication(): MainApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)