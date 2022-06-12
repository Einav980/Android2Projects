package com.example.rently.ui.screens.add_apartment

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.ApartmentCard
import com.example.rently.ui.theme.RentlyTheme

@Composable
fun AddApartmentScreen(viewModel: AddApartmentViewModel = hiltViewModel()) {
    var addressInput by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    RentlyTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(modifier = Modifier.fillMaxWidth().padding(10.dp), text = "Add apartment", style = MaterialTheme.typography.h5)
            Column(modifier = Modifier.width(350.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = addressInput,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Location"
                        )
                    },
                    label = { Text("Address") },
                    onValueChange = {
                        addressInput = it
                        viewModel.onSearchAddressChange(addressInput)
                    })

                if (viewModel.isLoading.value) {
                    CircularProgressIndicator()
                }
                if(viewModel.showPredictions.value){
                    LazyColumn(Modifier.border(1.dp, Color.Black)){
                        items(items = viewModel.predictions.value) { prediction ->
                            Surface(
                                modifier = Modifier
                                    .clickable {
                                        addressInput = prediction.description
                                        viewModel.hidePredictions()
                                        Toast
                                            .makeText(context, "Clicked", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.background)
                            ) {
                                Text(text = prediction.description, modifier = Modifier.padding(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddApartmentScreenPreview() {
    AddApartmentScreen()
}