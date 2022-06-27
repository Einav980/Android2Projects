package com.example.rently.ui.screens.manage_apartments

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.SharedViewModel
import com.example.rently.ui.components.ApartmentCard
import com.example.rently.ui.screens.PageTitleCard
import com.example.rently.ui.screens.manage_apartments.events.ManageApartmentsFormEvent
import com.example.rently.ui.theme.RentlyDrawerItemBackground
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.ApartmentPageType

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ManageApartmentsScreen(
    viewModel: ManageApartmentsViewModel = hiltViewModel(),
    onApartmentClicked: () -> Unit,
    sharedViewModel: SharedViewModel,
    onAddApartmentClicked: () -> Unit,
) {

    val context = LocalContext.current
    val state = viewModel.state

    var listError by remember { mutableStateOf(false) }
    var listLoading by remember { mutableStateOf(false) }
    var listSuccess by remember { mutableStateOf(false) }
    var removeSuccess by remember { mutableStateOf(false) }
    var removeError by remember { mutableStateOf(false) }
    var statusChangeSuccess by remember { mutableStateOf(false) }
    var statusChangeError by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ManageApartmentsViewModel.ValidationEvent.PageLoading -> {
                    listLoading = true
                }

                is ManageApartmentsViewModel.ValidationEvent.PageLoaded -> {
                    listLoading = false
                    listSuccess = true
                }

                is ManageApartmentsViewModel.ValidationEvent.PageError -> {
                    listLoading = false
                    listError = true
                }

                is ManageApartmentsViewModel.ValidationEvent.ApartmentDeleteError -> {
                    listLoading = false
                    removeError = true
                }

                is ManageApartmentsViewModel.ValidationEvent.ApartmentDeleteSuccess -> {
                    listLoading = false
                    removeSuccess = true
                }

                is ManageApartmentsViewModel.ValidationEvent.ApartmentStatusChangeSuccess -> {
                    listLoading = false
                    statusChangeSuccess = true
                }

                is ManageApartmentsViewModel.ValidationEvent.ApartmentStatusChangeError -> {
                    listLoading = false
                    statusChangeError = true
                }

            }
        }
    }

    RentlyTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    PageTitleCard(title = "Manage")
                },
                floatingActionButton = {
                    AddApartmentButton(
                        onClick = onAddApartmentClicked
                    )
                },
                content = {
                    if (listSuccess) {
                        if (state.apartments.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(4f),
                                contentPadding = PaddingValues(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(items = state.apartments) { apartment ->
                                    ApartmentCard(
                                        apartment = apartment,
                                        pageType = ApartmentPageType.UserManage,
                                        onApartmentClick = {
                                            sharedViewModel.setApartment(it)
                                            onApartmentClicked()
                                        },
                                        onDeleteApartment = {
                                            viewModel.onEvent(
                                                ManageApartmentsFormEvent.ApartmentDeleted(
                                                    it
                                                )
                                            )
                                        },
                                        onChangeApartmentStatus = {
                                            viewModel.onEvent(
                                                ManageApartmentsFormEvent.ApartmentStatusChanged(it)
                                            )
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
                                        text = "Could not remove apartment",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                        if (statusChangeError) {
                            AlertDialog(
                                onDismissRequest = { /* DO NOTHING */ },
                                buttons = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        TextButton(onClick = { statusChangeError = false }) {
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
                                        text = "Could not change apartment status",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                        if (statusChangeSuccess) {
                            Toast.makeText(
                                context,
                                "Apartment status changed",
                                Toast.LENGTH_SHORT
                            ).show()
                            statusChangeSuccess = false
                        }
                        if (removeSuccess) {
                            Toast.makeText(
                                context,
                                "Apartment deleted successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            removeSuccess = false
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (listLoading) {
                            CircularProgressIndicator()
                        }
                        if (listError) {
                            Text(
                                text = "Error loading apartments",
                                style = MaterialTheme.typography.body1,
                                color = Color.Gray
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AddApartmentButton(onClick: () -> Unit) {
    FloatingActionButton(
        backgroundColor = RentlyDrawerItemBackground,
        onClick = onClick
    ) {
        Icon(Icons.Filled.Add, "", modifier = Modifier.size(30.dp))
    }
}
