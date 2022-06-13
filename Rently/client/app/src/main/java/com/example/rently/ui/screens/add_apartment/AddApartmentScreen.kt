package com.example.rently.ui.screens.add_apartment

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.components.Counter
import com.example.rently.ui.theme.RentlyTheme
import java.io.ByteArrayOutputStream


@Composable
fun AddApartmentScreen(viewModel: AddApartmentViewModel = hiltViewModel()) {
    var addressInput by remember {
        mutableStateOf("")
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

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        if (imageUri != null) {
            bitmap.value = viewModel.getImageBitmap(context = context, imageUri = imageUri!!)
            imageEncodedString = convertImageToBase64(bitmap = bitmap.value!!)
            if (imageEncodedString.isNotEmpty()) {
                viewModel.uploadImage(imageEncodedString)
            }
        }
    }


    RentlyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), text = "Add apartment", style = MaterialTheme.typography.h5
            )
            Column(
                modifier = Modifier.width(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
                if (viewModel.showPredictions.value) {
                    LazyColumn(Modifier.border(1.dp, Color.Black)) {
                        items(items = viewModel.predictions) { prediction ->
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
                                Text(
                                    text = prediction.description,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            Button(modifier = Modifier.size(200.dp), onClick = { launcher.launch("image/*") }) {
                if (imageUri != null) {
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {

                            },
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    ImagePlaceHolder()
                }
            }

            Counter(value = counter, onValueIncrease = {counter++}, onValueDecrease = {counter--}, size = 30.dp, fontSize = 24.sp, label = "Bedrooms")
            Counter(value = counter, onValueIncrease = {counter++}, onValueDecrease = {counter--}, size = 30.dp, fontSize = 24.sp, label = "Toilets")
        }
    }
}

@Preview
@Composable
fun AddApartmentScreenPreview() {
    AddApartmentScreen()
}

fun convertImageToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    val encodedImage =
        try {
            Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (exception: Exception) {
            ""
        }

    return encodedImage
}

@Composable
fun ImagePlaceHolder() {
    Image(
        imageVector = Icons.Filled.Image,
        contentDescription = "Image",
        modifier = Modifier.fillMaxSize()
    )
}