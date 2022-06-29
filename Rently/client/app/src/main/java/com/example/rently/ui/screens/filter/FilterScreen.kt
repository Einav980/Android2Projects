package com.example.rently.ui.screens.filter

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.util.toRange
import com.example.rently.ui.components.*
import com.example.rently.ui.screens.apartments.ApartmentsViewModel
import com.example.rently.ui.screens.apartments.events.ApartmentsFormEvent
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.ui.theme.RoundedSquareShape
import com.example.rently.util.*
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun FilterScreen(
    viewModel: ApartmentsViewModel,
    onFilterApplied: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val state = viewModel.filterState.value

    var city = mutableStateOf("")
    var bedroomsNumberList = listOf("1+", "2+", "3+", "4+", "5+")
    var bathroomsNumberList = listOf("1+", "2+", "3+", "4+", "5+")

    RentlyTheme {
        Scaffold(
            topBar = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            TextButton(
                                onClick = onBackClicked
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Back",
                                    color = MaterialTheme.colors.primary,
                                )
                            }
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            TextButton(
                                onClick = { viewModel.onEvent(ApartmentsFormEvent.ClearFilter) },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.RestartAlt,
                                    contentDescription = "Reset",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Reset",
                                    color = MaterialTheme.colors.primary,
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            shape = RoundedSquareShape.large,
                            onClick = {
                                viewModel.onEvent(ApartmentsFormEvent.ApplyFilter)
                                onFilterApplied()
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = "Filter")
                            Text(text = "Filter")
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 12.dp,
                            end = 12.dp
                        )
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Section(title = "City", icon = Icons.Filled.LocationCity) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.weight(6f)){
                                RentlyTextField(
                                    modifier = Modifier
                                        .padding(5.dp),
                                    value = city.value,
                                    onValueChange = { city.value = it },
                                    label = "Add City",
                                )
                            }
                            Box(modifier = Modifier.weight(1f)){
                                Button(
                                    shape = RoundedSquareShape.large,
                                    onClick = {
                                        state.cities.add(city.value)
                                        city.value = "" },
                                    modifier = Modifier
                                        .fillMaxSize()
                                ){
                                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                                }
                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(items = state.cities) { _, value ->
                                    OutlinedChip(
                                        text= value
                                    )
                                }
                            }
                        }

                    }
                    Section(title = "Price", icon = Icons.Filled.Sell) {
                        val format = NumberFormat.getCurrencyInstance()
                        format.maximumFractionDigits = 0
                        format.currency = Currency.getInstance("ILS")
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            RangeSlider(
                                values = state.priceRange,
                                onValueChange = { viewModel.onEvent(
                                    ApartmentsFormEvent.PriceRangeChanged(it)
                                )},
                                valueRange = 0f..1f
                            )
                            Box(
                                modifier = Modifier.fillMaxSize(),
                            ){
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp),
                                    text = format.format((state.priceRange.toRange().lower * 10000).roundToInt())
                                )
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(8.dp),
                                    text = format.format((state.priceRange.toRange().upper * 10000).roundToInt())
                                )
                            }
                        }
                    }
                    Section(title = "Size", icon = Icons.Filled.Straighten) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Slider(
                                value = state.size,
                                onValueChange = {viewModel.onEvent(ApartmentsFormEvent.SizeChanged(it))},
                            )
                            Box(
                                modifier = Modifier.fillMaxSize(),
                            ){
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(8.dp),
                                    text = "+ ${ (state.size * 150).roundToInt() } sqft"
                                )
                            }
                        }
                    }
                    Section(title = "Property Type", icon = Icons.Filled.HomeWork) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(items = state.apartmentTypes) { index, apartmentType ->
                                    OutlinedChoiceChip(
                                        text = apartmentType.type,
                                        chosen = viewModel.selectedApartmentTypesIndexes.contains(index),
                                        onSelect = {
                                            if(viewModel.selectedApartmentTypesIndexes.contains(index))
                                            {
                                                viewModel.selectedApartmentTypesIndexes.remove(index)
                                            }
                                            else{
                                                viewModel.selectedApartmentTypesIndexes.add(index)
                                            }
                                        },
                                        icon = getApartmentTypeIcon(type = apartmentType.type)
                                    )
                                }
                            }
                        }
                    }
                    Section(title = "Number of bedroom", icon = Icons.Filled.Bed) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                itemsIndexed(items = bedroomsNumberList) { index, value ->
                                    OutlinedChoiceChip(
                                        text = value,
                                        chosen = index == state.numberOfBedrooms,
                                        onSelect = { viewModel.onEvent(ApartmentsFormEvent.NumberOfBedroomsChanged(index)) },
                                    )
                                }
                            }
                        }
                    }
                    Section(title = "Number of bathrooms", icon = Icons.Filled.Bathtub) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                itemsIndexed(items = bathroomsNumberList) { index, value ->
                                    OutlinedChoiceChip(
                                        text = value,
                                        chosen = index == state.numberOfBathrooms,
                                        onSelect = { viewModel.onEvent(ApartmentsFormEvent.NumberOfBathroomsChanged(index)) },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun AddApartmentScreenPreview() {
}


