package com.example.rently.ui.screens.watchList

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.rently.FilterSharedViewModel
import com.example.rently.SharedViewModel
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.ApartmentCard
import com.example.rently.ui.components.WatchListApartmentCard
import com.example.rently.ui.screens.PageTitleCard
import com.example.rently.ui.theme.RentlyDrawerItemBackground
import com.example.rently.ui.theme.RoundedSquareShape
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    onApartmentClicked: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var listError by remember { mutableStateOf(false) }
    var listLoading by remember { mutableStateOf(false) }
    var listSuccess by remember { mutableStateOf(false) }
    var removeSuccess by remember { mutableStateOf(false) }
    var removeError by remember { mutableStateOf(false) }


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

                WatchListViewModel.ValidationEvent.RemoveSuccess -> {
                    listLoading = false
                    removeSuccess = true
                }

                WatchListViewModel.ValidationEvent.RemoveError -> {
                    listLoading = false
                    removeError = true
                }
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
                                    navController = navController,
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
