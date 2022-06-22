package com.example.rently.ui.screens.watchList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.rently.ui.theme.RentlyDrawerItemBackground
import com.example.rently.ui.theme.RoundedSquareShape

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {

    viewModel.watchListApartments()
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopBarTitle()
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = viewModel.apartments) { apartment ->
                        WatchListApartmentCard(apartment = apartment, navController = navController, onApartmentClick = {
                            sharedViewModel.setApartment(it)
                            navController.navigate(Screen.SingleApartment.route)
                        })
                    }
                }
            }
        )
    }
}


@Composable
fun TopBarTitle() {
    TopAppBar(
        elevation = 50.dp,
        modifier = Modifier
            .wrapContentSize(),
        backgroundColor = Color.White,
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .weight(5f)
                .fillMaxWidth(),
            text = "Watch List",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 30.sp
        )

    }
}

//@Preview
//@Composable
//fun ApartmentsScreenPreview() {
//    val context = LocalContext.current
//    ApartmentsScreen(navController = NavHostController(context = context), )
//}