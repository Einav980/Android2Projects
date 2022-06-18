package com.example.rently.ui.screens.add_apartment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.config.APARTMENT_DESCRIPTION_MAX_LENGTH
import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.*
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.ui.theme.RoundedSquareShape
import com.example.rently.util.*
import java.io.ByteArrayOutputStream
import java.util.function.UnaryOperator


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddApartmentScreen(viewModel: AddApartmentViewModel = hiltViewModel()) {
    var addressInput by remember {
        mutableStateOf("")
    }

    var apartmentDescription by remember {
        mutableStateOf("")
    }

    var apartmentPrice by remember {
        mutableStateOf(0)
    }

    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var imageEncodedString by remember {
        mutableStateOf("")
    }

    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    var counter by remember {
        mutableStateOf(0)
    }

    var apartmentTypeList =
        listOf(
            ApartmentType(type = "Grass"),
            ApartmentType(type = "Apartment"),
            ApartmentType(type = "Cottage"),
        )
    var selectedApartmentTypeIndex by remember { mutableStateOf(0) }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        if (imageUri != null) {
            bitmap.value = getImageBitmap(context = context, imageUri = imageUri!!)
            imageEncodedString = convertImageToBase64(bitmap = bitmap.value!!)
            if (imageEncodedString.isNotEmpty()) {
                viewModel.uploadImage(imageEncodedString)
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Back")
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 50.dp, end = 50.dp)
                        ) {
                            RentlyTextField(
                                value = addressInput,
                                onValueChange = {
                                    addressInput = it
                                    viewModel.onSearchAddressChange(it)
                                },
                                label = "Address",
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.LocationOn,
                                        contentDescription = "Address"
                                    )
                                }
                            )
                            if (viewModel.isLoading.value) {
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }
                            if (viewModel.showPredictions.value) {
                                LazyColumn() {
                                    items(items = viewModel.predictions) { prediction ->
                                        Surface(
                                            modifier = Modifier
                                                .clickable {
                                                    addressInput = prediction.description
                                                    viewModel.hidePredictions()
                                                    viewModel.getAddressLocation(address = addressInput)
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
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(shape = RoundedSquareShape.large, onClick = {/*TODO*/ }) {
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
                                value = apartmentDescription,
                                onValueChange = { apartmentDescription = it },
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
                                if (imageUri != null) {
                                    Image(
                                        bitmap = bitmap.value!!.asImageBitmap(),
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
                                value = apartmentPrice.toString(),
                                onValueChange = { apartmentPrice = it.toInt() },
                                label = "Price",
                                keyboardType = KeyboardType.Number
                            )
                        }
                    }
                    Section(title = "Type", icon = Icons.Filled.HomeWork) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(items = apartmentTypeList) { index, apartmentType ->
                                    OutlinedChoiceChip(
                                        text = apartmentType.type,
                                        chosen = index == selectedApartmentTypeIndex,
                                        onSelect = { selectedApartmentTypeIndex = index },
                                        icon = getIconResourceByName(name = apartmentType.type)
                                    )
                                }
                            }
                        }
                    }
                    Section(title = "Options", icon = Icons.Filled.List) {
                        when (apartmentTypeList[selectedApartmentTypeIndex].type) {
                            "Apartment" -> {
                                ApartmentOptions()
                            }
                            else -> {
                                Text(text = "In Construction")
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
    AddApartmentScreen()
}

@Composable
fun ApartmentOptions() {
    ToggleRow(text = "Parking", onCheck = { /*TODO*/ })
    Spacer(modifier = Modifier.height(10.dp))
    ToggleRow(text = "Balcony", onCheck = { /*TODO*/ })
    Spacer(modifier = Modifier.height(10.dp))
    ToggleRow(text = "Animals Allowed", onCheck = { /*TODO*/ })
    Spacer(modifier = Modifier.height(10.dp))
    ToggleRow(text = "Furnished", onCheck = { /*TODO*/ })
}

