package com.example.rently.ui.screens.apartments

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.rently.FilterSharedViewModel
import com.example.rently.SharedViewModel
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.ApartmentCard
import com.example.rently.ui.screens.apartments.events.ApartmentsFormEvent
import com.example.rently.ui.theme.RentlyCardShape
import com.example.rently.ui.theme.RentlyMapButtonBackgroundColor
import com.example.rently.ui.theme.RentlyMapButtonForegroundColor
import com.example.rently.util.Constants

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
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                MapButton(
                    onClick = onMapClicked
                )
            },
            topBar = {
                TopBarTitle(navController)
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

@Composable
fun TopBarTitle(navController: NavHostController) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 30.dp, shape = RentlyCardShape.large) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.House,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = Constants.APARTMENTS_PAGE_TITLE,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun ApartmentsScreenPreview() {
//    val context = LocalContext.current
//    ApartmentsScreen(navController = NavHostController(context = context), )
//}