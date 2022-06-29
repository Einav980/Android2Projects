package com.example.rently.ui.screens.single_apartment

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.rently.SharedViewModel
import com.example.rently.model.User
import com.example.rently.ui.components.BackButton
import com.example.rently.ui.components.RentlyChip
import com.example.rently.ui.components.SquareChip
import com.example.rently.ui.theme.RentlyGrayColor
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.ui.theme.RoundedSquareShape
import com.example.rently.util.priceToCurrency
import com.google.android.gms.maps.model.LatLng

@Composable
fun SingleApartmentScreen(
    viewModel: SingleApartmentViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onBackClicked: () -> Unit,
    onMapClicked: (LatLng) -> Unit
) {
    val apartment = sharedViewModel.apartment

    LaunchedEffect(key1 = sharedViewModel.apartment) {
        if (apartment != null) {
            viewModel.getApartmentUserInfo(apartment.userId)
        }
    }

    var showContactDialog by remember {
        mutableStateOf(false)
    }

    val state = viewModel.state

    if (apartment != null) {
        val painter = rememberImagePainter(
            data = apartment.imageUrl,
            builder = {
                crossfade(500)
            }
        )
        val painterState = painter.state

        RentlyTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                // Image and basic info section
                Box(
                    modifier = Modifier
                        .weight(3f)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Image(
                            painter = painter,
                            contentScale = ContentScale.Crop,
                            contentDescription = "Image",
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .fillMaxSize()
                                .graphicsLayer {
                                    alpha = 0.99f
                                }
                                .drawWithContent {
                                    val colors = listOf(
                                        Color.Black,
                                        Color.Black,
                                        Color.Transparent
                                    )
                                    drawContent()
                                    drawRect(
                                        brush = Brush.verticalGradient(colors),
                                        blendMode = BlendMode.DstIn
                                    )
                                }
                        )
                        if (painterState is AsyncImagePainter.State.Loading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Text(
                                text = apartment.type,
                                style = MaterialTheme.typography.h4,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row() {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = "Location",
                                    tint = RentlyGrayColor
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = apartment.address,
                                    style = MaterialTheme.typography.body1,
                                    color = RentlyGrayColor,
                                    fontWeight = FontWeight.Bold,

                                    )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                ) {
                    Column() {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            RentlyChip(text = "${priceToCurrency(apartment.price)} / mon", textStyle = MaterialTheme.typography.h5)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 15.dp)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            SquareChip(
                                icon = Icons.Outlined.Bathtub,
                                foregroundColor = MaterialTheme.colors.primary,
                                text = "${apartment.numberOfBaths} Bath"
                            )
                            SquareChip(
                                icon = Icons.Outlined.Bed,
                                foregroundColor = MaterialTheme.colors.primary,
                                text = "${apartment.numberOfBeds} Beds"
                            )
                            if (apartment.isFurnished) {
                                SquareChip(
                                    icon = Icons.Outlined.Living,
                                    foregroundColor = MaterialTheme.colors.primary,
                                    text = "Furnished"
                                )
                            }
                            if (apartment.hasParking) {
                                SquareChip(
                                    icon = Icons.Outlined.LocalParking,
                                    foregroundColor = MaterialTheme.colors.primary,
                                    text = "Parking Included"
                                )
                            }
                            if (apartment.hasBalcony) {
                                SquareChip(
                                    icon = Icons.Outlined.Balcony,
                                    foregroundColor = MaterialTheme.colors.primary,
                                    text = "Has balcony"
                                )
                            }
                            if (apartment.isAnimalFriendly) {
                                SquareChip(
                                    icon = Icons.Outlined.Pets,
                                    foregroundColor = MaterialTheme.colors.primary,
                                    text = "Animals Friendly"
                                )
                            }
                        }
                    }
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .weight(3f)
                            .verticalScroll(rememberScrollState())
                    )
                    {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = apartment.description,
                            lineHeight = 35.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            elevation = ButtonDefaults.elevation(defaultElevation = 5.dp),
                            onClick = {
                                showContactDialog = true
                            },
                            shape = RoundedSquareShape.large
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = "Contact Information"
                            )
                            Text(
                                text = "Contact Information",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                FloatingActionButton(
                    onClick = { onMapClicked(apartment.location) },
                    shape = RoundedSquareShape.large,
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "SeeOnMap",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(10.dp),
                contentAlignment = Alignment.TopStart
            ) {
               BackButton(onClick = onBackClicked)
            }
        }
    } else {
        RentlyTheme {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
    if (showContactDialog) {
        ContactDialog(user = state.user, onCloseDialog = { showContactDialog = false })
    }
}

@Composable
fun ContactDialog(user: User? = null, onCloseDialog: () -> Unit) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Contact Information",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Name: ${user?.firstname} ${user?.lastname}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Phone: ${user?.phone}")
            }
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = onCloseDialog) {
                    Text(text = "Close")
                }
            }
        }
    )
}
//@Preview(showBackground = true)
//@Composable
//fun NewSingleApartmentScreenPreview() {
//    val apartment = Constants.apartment
//    RentlyTheme() {
//        NewSingleApartmentScreen(apartment = apartment)
//    }
//}