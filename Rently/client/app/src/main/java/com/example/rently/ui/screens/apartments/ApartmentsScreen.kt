package com.example.rently.ui.screens.apartments

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.TravelExplore
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
import androidx.navigation.NavHostController
import com.example.rently.FilterSharedViewModel
import com.example.rently.SharedViewModel
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.ApartmentCard
import com.example.rently.ui.screens.PageTitleCard
import com.example.rently.ui.screens.apartments.events.ApartmentsFormEvent
import com.example.rently.ui.theme.RentlyMapButtonBackgroundColor
import com.example.rently.ui.theme.RentlyMapButtonForegroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ApartmentsScreen(
    viewModel: ApartmentsViewModel = hiltViewModel(),
    navController: NavHostController,
    onApartmentClicked: () -> Unit,
    sharedViewModel: SharedViewModel,
    filterSharedViewModel: FilterSharedViewModel,
    onMapClicked: () -> Unit,
) {

    val filterState = filterSharedViewModel.state
    val state = viewModel.state.value
    val context = LocalContext.current

    var listError by rememberSaveable { mutableStateOf(false) }
    var listLoading by rememberSaveable { mutableStateOf(false) }
    var listSuccess by rememberSaveable { mutableStateOf(false) }
    var removeWatchlistSuccess by rememberSaveable { mutableStateOf(false) }
    var removeWatchlistError by rememberSaveable { mutableStateOf(false) }
    var addWatchlistSuccess by rememberSaveable { mutableStateOf(false) }
    var addWatchlistError by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ApartmentsViewModel.ValidationEvent.PageLoading -> {
                    listLoading = true
                }

                is ApartmentsViewModel.ValidationEvent.PageLoaded -> {
                    listLoading = false
                    listSuccess = true
                }

                is ApartmentsViewModel.ValidationEvent.PageError -> {
                    listLoading = false
                    listError = true
                }

                is ApartmentsViewModel.ValidationEvent.RemoveApartmentToWatchlistError -> {
                    listLoading = false
                    removeWatchlistError = true
                }

                is ApartmentsViewModel.ValidationEvent.RemoveApartmentToWatchlistSuccess -> {
                    listLoading = false
                    removeWatchlistSuccess = true
                }

                is ApartmentsViewModel.ValidationEvent.AddApartmentToWatchlistSuccess -> {
                    listLoading = false
                    addWatchlistSuccess = true
                }

                is ApartmentsViewModel.ValidationEvent.AddApartmentToWatchlistError -> {
                    listLoading = false
                    addWatchlistError = true
                }

            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                MapButton(
                    onClick = onMapClicked
                )
            },
            topBar = {
                PageTitleCard(
                    title = "Discover"/*Constants.APARTMENTS_PAGE_TITLE*/,
                    icon = Icons.Filled.Home
                )
            },
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${state.apartments.size} properties",
                            style = MaterialTheme.typography.body1
                        )
                        OutlinedButton(
                            onClick = {
                                navController.navigate(Screen.Filter.route) {
                                    popUpTo(Screen.Filter.route)
                                }
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                            border = BorderStroke(0.dp, Color.Transparent)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FilterAlt,
                                contentDescription = null,
                                tint = if (state.isFiltered) MaterialTheme.colors.primary else Color.Gray
                            )
                        }
                    }
                    if(listSuccess){
                        if (state.apartments.isNotEmpty()){
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentPadding = PaddingValues(12.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(items = state.apartments) { apartment ->
                                    ApartmentCard(
                                        apartment = apartment,
                                        onApartmentClick = {
                                            sharedViewModel.setApartment(it)
                                            onApartmentClicked()
                                        },
                                        isWatched = state.userWatchlist.any { item -> item.apartmentId == apartment._id },
                                        onAddToWatchlist = {
                                            viewModel.onEvent(
                                                ApartmentsFormEvent.AddToWatchlist(
                                                    it
                                                )
                                            )
                                        },
                                        onRemoveFromWatchlist = {
                                            viewModel.onEvent(
                                                ApartmentsFormEvent.RemoveFromWatchList(
                                                    it
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        else{
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "Apartment list is empty",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.Gray
                                )
                            }
                        }
                        if (removeWatchlistError) {
                            AlertDialog(
                                onDismissRequest = { /* DO NOTHING */ },
                                buttons = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        TextButton(onClick = { removeWatchlistError = false }) {
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
                        if (addWatchlistError) {
                            AlertDialog(
                                onDismissRequest = { /* DO NOTHING */ },
                                buttons = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        TextButton(onClick = { addWatchlistError = false }) {
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
                                        text = "Could not add apartment to watchlist",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                        if (addWatchlistSuccess) {
                            Toast.makeText(
                                context,
                                "Apartment add to watchlist successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            addWatchlistSuccess = false
                        }
                        if (removeWatchlistSuccess) {
                            Toast.makeText(
                                context,
                                "Apartment removed from watchlist successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            removeWatchlistSuccess = false
                        }

                    }else{
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            if(listError){
                                Text(
                                    text = "Could not load apartments",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.Gray
                                )
                            }
                            if(listLoading){
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun MapButton(onClick: () -> Unit) {
    FloatingActionButton(
        backgroundColor = RentlyMapButtonBackgroundColor,
        onClick = onClick
    ) {
        Icon(
            Icons.Outlined.TravelExplore,
            null,
            modifier = Modifier.size(30.dp),
            tint = RentlyMapButtonForegroundColor
        )
    }
}

//@Preview
//@Composable
//fun ApartmentsScreenPreview() {
//    val context = LocalContext.current
//    ApartmentsScreen(navController = NavHostController(context = context), )
//}