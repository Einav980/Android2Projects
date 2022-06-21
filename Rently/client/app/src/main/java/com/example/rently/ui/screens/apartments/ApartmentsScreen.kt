package com.example.rently.ui.screens.apartments

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
import com.example.rently.ui.theme.RentlyDrawerItemBackground
import com.example.rently.ui.theme.RoundedSquareShape

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ApartmentsScreen(
    viewModel: ApartmentsViewModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    filterSharedViewModel: FilterSharedViewModel
) {
//    var apartments = listOf<Apartment>(
//        Apartment("Tel-Aviv", price = 7800, numberOfRooms = 3, address = "Dov Nov 16", numberOfBaths = 1, numberOfBeds = 2, size = 54, imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1"),
//        Apartment("Bat Yam", price = 5500, numberOfRooms = 2, address = "Poetry Rachel 22", numberOfBaths = 1, numberOfBeds = 1, size = 88, imageUrl = "https://q-xx.bstatic.com/xdata/images/hotel/840x460/200051570.jpg?k=52c15a2aa674672a1440bd1d596e57f918b2fbdc0c942b6a5add5a6dc0f4a165"),
//        Apartment("Ramat-Gan", price = 6300, numberOfRooms = 2, address = "35 Bialik St.", numberOfBaths = 2, numberOfBeds = 2, size = 95,  imageUrl = "https://media.istockphoto.com/photos/exterior-view-of-modern-apartment-building-offering-luxury-rental-in-picture-id1322575582?b=1&k=20&m=1322575582&s=170667a&w=0&h=bGCtLpgCEorQuVdW2lbWguNZHcOGPePSwDibgbgyh0U="),
//        Apartment("Ramat-Gan", price = 6300, numberOfRooms = 2, address = "35 Bialik St.", numberOfBaths = 2, numberOfBeds = 2, size = 95,  imageUrl = "https://media.istockphoto.com/photos/exterior-view-of-modern-apartment-building-offering-luxury-rental-in-picture-id1322575582?b=1&k=20&m=1322575582&s=170667a&w=0&h=bGCtLpgCEorQuVdW2lbWguNZHcOGPePSwDibgbgyh0U=")
//    )
//    var apartments = Apartment(_id = "test",status= ApartmentStatus.Available.status,  city= "Tel-Aviv", price = 7800, numberOfRooms = 3, address = "Dov Nov 16", numberOfBaths = 1, numberOfBeds = 2, size = 54, imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1")
//    viewModel.addApartment(apartments)

    val state = filterSharedViewModel.state

    viewModel.listApartments(state)
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                FloatingButton()
            },
            topBar = {
                TopBarTitle(navController)
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = viewModel.apartments) { apartment ->
                        ApartmentCard(apartment = apartment, navController = navController, onApartmentClick = {
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
fun FloatingButton() {
    FloatingActionButton(
        modifier = Modifier.wrapContentSize(),
        backgroundColor = RentlyDrawerItemBackground,
        onClick = { }
    ) {
        Icon(Icons.Filled.Place, "", modifier = Modifier.size(30.dp))
    }
}

@Composable
fun TopBarTitle(navController: NavHostController) {
    TopAppBar(
        elevation = 50.dp,
        modifier = Modifier
            .wrapContentSize(),
        backgroundColor = Color.White,
    ) {
        Button(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .fillMaxWidth(),
            shape = RoundedSquareShape.large,
            onClick = {
                navController.navigate(Screen.Filter.route) {
                    popUpTo(Screen.Filter.route)
                }
            }
        ) {
            Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = "Filter")
        }
        Text(
            modifier = Modifier
                .padding(10.dp)
                .weight(5f)
                .fillMaxWidth(),
            text = "Apartments",
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