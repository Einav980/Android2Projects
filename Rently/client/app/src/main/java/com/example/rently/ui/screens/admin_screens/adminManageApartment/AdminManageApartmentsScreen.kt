package com.example.rently.ui.screens.admin_screens.adminManageApartment

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.rently.SharedViewModel
import com.example.rently.model.Apartment
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.ApartmentCard
import com.example.rently.ui.screens.main.PageTitleCard
import com.example.rently.ui.theme.RentlyCardShape
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.ApartmentPageType
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminManageApartmentsScreen(
    viewModel: AdminManageApartmentsViewModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    onApartmentClicked: () -> Unit,
) {

    RentlyTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    PageTitleCard(title = "Manage requests")
                },
                content = {
                    if (viewModel.apartments.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items = viewModel.apartments) { apartment ->
                                val approve = SwipeAction(
                                    onSwipe = {
                                        viewModel.onApproveSwipe(apartment)
                                        Log.d("swipe", "Approve")
                                    },
                                    icon = {
                                        Icon(
                                            modifier = Modifier.padding(16.dp),
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    },
                                    background = Color.Green
                                )

                                val reject = SwipeAction(
                                    onSwipe = {
                                        viewModel.onRejectSwipe(apartment)
                                        Log.d("swipe", "Reject")
                                    },
                                    icon = {
                                        Icon(
                                            modifier = Modifier.padding(16.dp),
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    },
                                    background = Color.Red
                                )

                                SwipeableActionsBox(
                                    swipeThreshold = 100.dp,
                                    startActions = listOf(approve),
                                    endActions = listOf(reject),
                                    modifier = Modifier.clip(RentlyCardShape.large)
                                ) {
                                    ApartmentCard(
                                        apartment = apartment,
                                        pageType = ApartmentPageType.AdminManage,
                                        onApartmentClick = {
                                            sharedViewModel.setApartment(it)
                                            onApartmentClicked()
                                        },
                                    )
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No pending apartments to review",
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