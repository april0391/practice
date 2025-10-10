package com.example.androidplayground.ui.itementry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidplayground.R
import com.example.androidplayground.ui.AppViewModelProvider
import com.example.androidplayground.ui.components.DefaultTopAppBar
import com.example.androidplayground.ui.navigation.AppDestination

object ItemEntryDestination : AppDestination {
    override val route = "item_entry"
    override val title = "Item Entry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = ItemEntryDestination.title,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            OutlinedTextField(
                value = viewModel.itemDetailsState.name,
                onValueChange = { viewModel.itemDetailsState.copy(name = it) },
                label = { Text("name") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            TextField(value = viewModel.itemDetailsState.price, onValueChange = {
                viewModel.updateItemDetailsState(
                    viewModel.itemDetailsState.copy(price = it)
                )
            })
            TextField(value = viewModel.itemDetailsState.quantity, onValueChange = {
                viewModel.updateItemDetailsState(
                    viewModel.itemDetailsState.copy(quantity = it)
                )
            })
            Button(onClick = { }) {
                Text("Save")
            }
        }
    }
}