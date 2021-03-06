package com.example.rently.ui.screens.add_apartment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.components.*
import com.example.rently.ui.screens.add_apartment.events.AddApartmentFormEvent
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.ui.theme.RoundedSquareShape
import com.example.rently.util.getApartmentTypeIcon
import com.example.rently.util.getImageBitmap


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddApartmentScreen(
    viewModel: AddApartmentViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onAddApartmentFinished: () -> Unit = {}
) {

    val context = LocalContext.current
    val state = viewModel.state

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onEvent(
                AddApartmentFormEvent.ImageBitmapChanged(
                    getImageBitmap(
                        context = context,
                        imageUri = uri
                    )
                )
            )
        }
    }


    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is AddApartmentViewModel.ValidationEvent.ApartmentUploading -> {
                    showLoadingDialog = true
                }

                is AddApartmentViewModel.ValidationEvent.ApartmentUploadSuccess -> {
                    showLoadingDialog = false
                    showSuccessDialog = true
                }

                is AddApartmentViewModel.ValidationEvent.ApartmentUploadError -> {
                    showLoadingDialog = false
                    showErrorDialog = true
                }
            }
        }
    }

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
                            .padding(8.dp)
                    ) {
                        TextButton(onClick = onBackClicked) {
                            Text(text = "Back")
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            text = "Add Apartment",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 50.dp, end = 50.dp)
                        ) {
                            RentlyTextField(
                                value = state.apartmentAddress,
                                onValueChange = {
                                    viewModel.onEvent(AddApartmentFormEvent.AddressChanged(it))
                                },
                                label = "Address",
                                isError = state.addressError != null,
                                errorMessage = state.addressError ?: "",
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.LocationOn,
                                        contentDescription = "Address"
                                    )
                                }
                            )
                            if (viewModel.placesLoading.value) {
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }
                            if (state.showPredictions) {
                                LazyColumn() {
                                    items(items = viewModel.predictions) { prediction ->
                                        Surface(
                                            modifier = Modifier
                                                .clickable {
                                                    viewModel.onEvent(
                                                        AddApartmentFormEvent.AddressClicked(
                                                            prediction.description
                                                        )
                                                    )
                                                    viewModel.onEvent(
                                                        AddApartmentFormEvent.CityChanged(
                                                            prediction.terms[2].value
                                                        )
                                                    )
                                                    viewModel.onEvent(AddApartmentFormEvent.HidePredictions)
                                                }
                                                .fillMaxWidth()
                                                .background(MaterialTheme.colors.background)
                                        ) {
                                            Text(
                                                text = prediction.description,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }
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
                            onClick = { viewModel.onEvent(AddApartmentFormEvent.Submit) }) {
                            Icon(imageVector = Icons.Filled.Done, contentDescription = "Done")
                            Text(text = "Done")
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
                    Section(title = "Description", icon = Icons.Filled.Description) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            RentlyTextField(
                                value = state.apartmentDescription,
                                onValueChange = {
                                    viewModel.onEvent(
                                        AddApartmentFormEvent.DescriptionChanged(it)
                                    )
                                },
                                isError = state.descriptionError != null,
                                errorMessage = state.descriptionError ?: "",
                                label = "Description"
                            )
                        }
                    }
                    Section(title = "Image", icon = Icons.Filled.Image) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedSquareShape.large)
                                    .background(Color.LightGray)
                                    .size(150.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (state.apartmentImageBitmap != null) {
                                    Image(
                                        bitmap = state.apartmentImageBitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                    )
                                } else {
                                    ImagePlaceHolder()
                                }
                            }
                            Button(
                                onClick = { launcher.launch("image/*") },
                                shape = RoundedSquareShape.large
                            ) {
                                Text(text = "Select")
                            }
                        }
                    }
                    Section(title = "Price", icon = Icons.Filled.Sell) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            RentlyTextField(
                                value = state.apartmentPrice.toString(),
                                onValueChange = {
                                    viewModel.onEvent(
                                        AddApartmentFormEvent.PriceChanged(
                                            if (it.isNotEmpty()) it.toInt() else 0
                                        )
                                    )
                                },
                                label = "Price",
                                isError = state.priceError != null,
                                errorMessage = state.priceError ?: "",
                                keyboardType = KeyboardType.Number
                            )
                        }
                    }
                    Section(title = "Size", icon = Icons.Filled.Straighten) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            RentlyTextField(
                                value = state.apartmentSize.toString(),
                                onValueChange = {
                                    viewModel.onEvent(
                                        AddApartmentFormEvent.SizeChanged(
                                            if (it.isNotEmpty()) it.toInt() else 0
                                        )
                                    )
                                },
                                label = "Sqft",
                                isError = state.sizeError != null,
                                errorMessage = state.sizeError ?: "",
                                keyboardType = KeyboardType.Number
                            )
                        }
                    }
                    Section(title = "Type", icon = Icons.Filled.HomeWork) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            if (state.isApartmentTypesLoading) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    itemsIndexed(items = state.apartmentTypes) { index, apartmentType ->
                                        OutlinedChoiceChip(
                                            text = apartmentType.type,
                                            chosen = index == state.selectedApartmentTypeIndex,
                                            onSelect = {
                                                viewModel.onEvent(
                                                    AddApartmentFormEvent.ApartmentTypeChanged(
                                                        index
                                                    )
                                                )
                                            },
                                            icon = getApartmentTypeIcon(type = apartmentType.type)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Section(title = "Rooms", icon = Icons.Filled.Room) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Counter(
                                value = state.numberOfBedrooms,
                                onValueChanged = {
                                    viewModel.onEvent(
                                        AddApartmentFormEvent.BedroomsAmountChanged(it)
                                    )
                                },
                                icon = Icons.Filled.Bed
                            )
                            Counter(
                                value = state.numberOfBathrooms,
                                onValueChanged = {
                                    viewModel.onEvent(
                                        AddApartmentFormEvent.BathroomsAmountChanged(it)
                                    )
                                },
                                icon = Icons.Filled.Bathtub
                            )
                        }
                    }
                    Section(title = "Options", icon = Icons.Filled.List) {
                        Options(
                            parkingOptionChecked = state.hasParking,
                            balconyOptionChecked = state.hasBalcony,
                            animalsOptionChecked = state.isAnimalFriendly,
                            furnishedOptionChecked = state.isFurnished,
                            onParkingOptionToggle = {
                                viewModel.onEvent(
                                    AddApartmentFormEvent.HasParkingChanged(
                                        it
                                    )
                                )
                            },
                            onBalconyOptionToggle = {
                                viewModel.onEvent(
                                    AddApartmentFormEvent.HasBalconyChanged(
                                        it
                                    )
                                )
                            },
                            onAnimalsOptionToggle = {
                                viewModel.onEvent(
                                    AddApartmentFormEvent.IsAnimalFriendlyChanged(
                                        it
                                    )
                                )
                            },
                            onFurnishedOptionToggle = {
                                viewModel.onEvent(
                                    AddApartmentFormEvent.IsFurnishedChanged(
                                        it
                                    )
                                )
                            })
                    }
                }
            }
        }
        if (showLoadingDialog) {
            AlertDialog(
                onDismissRequest = { /* DO NOTHING */ },
                title = {
                    Text(
                        text = "Uploading Apartment",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                buttons = {},
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                })
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { /* DO NOTHING */ },
                buttons = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = {
                            showSuccessDialog = false
                            onAddApartmentFinished()
                        }) {
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
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Success",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Your apartment has been added succesfully!",
                        textAlign = TextAlign.Center
                    )
                })
        }
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { /* DO NOTHING */ },
                buttons = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { showErrorDialog = false }) {
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
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.height(10.dp))
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
                        text = "Encountered an error while uploading your apartment\nPlease try again later",
                        textAlign = TextAlign.Center
                    )
                })
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun AddApartmentScreenPreview() {
    AddApartmentScreen()
}

@Composable
fun Options(
    parkingOptionChecked: Boolean,
    balconyOptionChecked: Boolean,
    animalsOptionChecked: Boolean,
    furnishedOptionChecked: Boolean,
    onParkingOptionToggle: (Boolean) -> Unit,
    onBalconyOptionToggle: (Boolean) -> Unit,
    onAnimalsOptionToggle: (Boolean) -> Unit,
    onFurnishedOptionToggle: (Boolean) -> Unit,
) {
    ToggleRow(
        text = "Parking",
        isChecked = parkingOptionChecked,
        onCheckedChange = { onParkingOptionToggle(it) })
    Spacer(modifier = Modifier.height(10.dp))
    ToggleRow(
        text = "Balcony",
        isChecked = balconyOptionChecked,
        onCheckedChange = { onBalconyOptionToggle(it) })
    Spacer(modifier = Modifier.height(10.dp))
    ToggleRow(
        text = "Animals Allowed",
        isChecked = animalsOptionChecked,
        onCheckedChange = { onAnimalsOptionToggle(it) }
    )
    Spacer(modifier = Modifier.height(10.dp))
    ToggleRow(
        text = "Furnished",
        isChecked = furnishedOptionChecked,
        onCheckedChange = { onFurnishedOptionToggle(it) }
    )
}

