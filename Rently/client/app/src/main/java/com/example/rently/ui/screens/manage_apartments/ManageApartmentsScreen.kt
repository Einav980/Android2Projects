package com.example.rently.ui.screens.manage_apartments

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.rently.SharedViewModel
import com.example.rently.navigation.Screen
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

    var showLoadingProgress by remember { mutableStateOf(false) }
    var deleteInProgress by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ManageApartmentsViewModel.ValidationEvent.PageLoading -> {
                    showLoadingProgress = true
                }

                is ManageApartmentsViewModel.ValidationEvent.PageLoaded -> {
                    showLoadingProgress = false
                }

                is ManageApartmentsViewModel.ValidationEvent.ApartmentDeleteError -> {
                    showErrorDialog = true
                }

                is ManageApartmentsViewModel.ValidationEvent.ApartmentDeleteSuccess -> {
                    showSuccessDialog = true
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
                    if (showLoadingProgress) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
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
                                    viewModel.onEvent(ManageApartmentsFormEvent.ApartmentDeleted(it))
                                },
                                onChangeApartmentStatus = {
                                    viewModel.onEvent(
                                        ManageApartmentsFormEvent.ApartmentStatusChanged(it)
                                    )
                                }
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
