package com.example.rently.ui.screens.watchList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.SharedViewModel
import com.example.rently.ui.components.WatchListApartmentCard
import com.example.rently.ui.screens.main.PageTitleCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onApartmentClicked: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var listError by rememberSaveable { mutableStateOf(false) }
    var listLoading by rememberSaveable { mutableStateOf(false) }
    var listSuccess by rememberSaveable { mutableStateOf(false) }
    var removeError by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {

                WatchListViewModel.ValidationEvent.PageLoading -> {
                    listLoading = true
                }

                WatchListViewModel.ValidationEvent.PageError -> {
                    listLoading = false
                    listError = true
                }

                WatchListViewModel.ValidationEvent.PageSuccess -> {
                    listLoading = false
                    listSuccess = true
                }

                WatchListViewModel.ValidationEvent.RemoveError -> {
                    listLoading = false
                    removeError = true
                }
                else -> {}
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                PageTitleCard(title = "Watch List", icon = Icons.Filled.Visibility)
            },
            content = {
                if (listSuccess) {
                    if (state.apartments.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items = state.apartments) { apartment ->
                                WatchListApartmentCard(
                                    apartment = apartment,
                                    onApartmentClick = {
                                        sharedViewModel.setApartment(it)
                                        onApartmentClicked()
                                    },
                                    onRemoveApartment = {
                                        viewModel.removeWatchListApartments(it)
                                    }
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Your watchlist is empty",
                                style = MaterialTheme.typography.body1,
                                color = Color.Gray
                            )
                        }
                    }
                    if (removeError) {
                        AlertDialog(
                            onDismissRequest = { /* DO NOTHING */ },
                            buttons = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    TextButton(onClick = { removeError = false }) {
                                        Text("OK")
                                    }
                                }
                            },
                            title = {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Error",
                                        style = MaterialTheme.typography.h5,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            },
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Could not remove apartment from watchlist",
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                    if (!removeError){
                        listLoading = false
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (listError) {
                        Text(
                            text = "Error loading watchlist",
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray
                        )
                    }
                    if (listLoading) {
                        CircularProgressIndicator()
                    }
                }
            }
        )
    }
}
