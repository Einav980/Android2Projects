package com.example.rently.ui.screens.manageApartments

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.rently.SharedViewModel
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.ApartmentCard
import com.example.rently.ui.theme.RentlyDrawerItemBackground
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.ApartmentPageType

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ManageApartmentsScreen(
    viewModel: ManageApartmentsViewModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    viewModel.listUserApartments()
    RentlyTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopBarTitle()
                },
                floatingActionButton = {
                    FloatingButton()
                },
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(4f),
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items = viewModel.apartments) { apartment ->
                            ApartmentCard(
                                apartment = apartment,
                                pageType = ApartmentPageType.UserManage,
                                navController = navController,
                                onApartmentClick = {
                                    sharedViewModel.setApartment(it)
                                    navController.navigate(Screen.SingleApartment.route)
                                },
                                onDeleteApartment = {
                                    viewModel.deleteApartment(it)
                                },
                                onChangeApartmentStatus= {
                                    viewModel.changeApartmentStatus(it)
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
fun FloatingButton() {
    FloatingActionButton(
        modifier = Modifier.wrapContentSize(),
        backgroundColor = RentlyDrawerItemBackground,
        onClick = { }
    ) {
        Icon(Icons.Filled.Add, "", modifier = Modifier.size(30.dp))
    }
}

@Composable
fun TopBarTitle() {
    TopAppBar(
        elevation = 50.dp,
        modifier = Modifier
            .wrapContentSize(),
        backgroundColor = Color.White
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            text = "My Apartments",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 30.sp
        )
    }
}