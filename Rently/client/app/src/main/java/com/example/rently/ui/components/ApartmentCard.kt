package com.example.rently.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material.icons.outlined.Swipe
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.rently.model.Apartment
import com.example.rently.ui.theme.*
import com.example.rently.util.ApartmentPageType
import com.example.rently.util.ApartmentStatus
import java.text.NumberFormat
import java.util.*

@Composable
fun ApartmentCard(
    navController: NavController,
    pageType: ApartmentPageType = ApartmentPageType.Explore,
    apartment: Apartment,
    onApartmentClick: (apartment: Apartment) -> Unit,
    onDeleteApartment: (apartment: Apartment) -> Unit = {},
    onChangeApartmentStatus: (apartment: Apartment) -> Unit ={}
) {

    RentlyApartmentCardTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clickable { /* TODO: Click on apartment function */
                    onApartmentClick(apartment)
                },
            elevation = 10.dp,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxWidth()
                ) {
                    ApartmentImage(url = apartment.imageUrl)
                    if (pageType != ApartmentPageType.Explore) {
                        if (apartment.status.isNotEmpty()) {
                            ApartmentStatusBadge(apartmentStatus = ApartmentStatus.valueOf(apartment.status))
                        }
                        if(pageType == ApartmentPageType.UserManage)
                            DeleteApartmentBadge(apartment, onDeleteApartment)
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 3.dp, bottom = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = apartment.city,
                        modifier = Modifier.weight(5f),
                        style = MaterialTheme.typography.subtitle2,
                        color = RentlySubtitleTextColor
                    )
                    Text(
                        text = "${apartment.numberOfBeds} Beds",
                        modifier = Modifier
                            .weight(2f)
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        color = RentlySubtitleTextColor
                    )
                    Divider(
                        modifier = Modifier
                            .height(15.dp)
                            .width(1.dp)
                    )
                    Text(
                        text = "${apartment.numberOfBaths} Baths",
                        modifier = Modifier
                            .weight(2f)
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        color = RentlySubtitleTextColor
                    )
                    Divider(
                        modifier = Modifier
                            .height(15.dp)
                            .width(1.dp)
                    )
                    Text(
                        text = "${apartment.size} sqft",
                        modifier = Modifier
                            .weight(3f)
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        color = RentlySubtitleTextColor
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = apartment.address,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(2f),
                    )
                    val format = NumberFormat.getCurrencyInstance()
                    val apartmentCardColor =
                        if (pageType != ApartmentPageType.Explore) {
                            if (apartment.status != ApartmentStatus.Available.status) {
                                Color.Gray
                            } else {
                                MaterialTheme.colors.primary
                            }
                        } else {
                            MaterialTheme.colors.primary
                        }
                    format.maximumFractionDigits = 0
                    format.currency = Currency.getInstance("ILS")
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                            .background(apartmentCardColor)
                            .padding(10.dp)
                            .clip(MaterialTheme.shapes.medium),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = format.format(apartment.price),
                            style = MaterialTheme.typography.h6,
                            color = Color.White,
                            letterSpacing = 1.1.sp
                        )
                    }
                }
            }
            if (pageType == ApartmentPageType.UserManage && apartment.status != null) {
                switchApartmentStatus(apartment , onChangeApartmentStatus)
            }
            if (pageType == ApartmentPageType.AdminManage){
                swipeIndications()
            }
        }
    }
}

@Composable
fun swipeIndications() {
    RentlyApartmentCardTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(50.dp)
                    .shadow(elevation = 50.dp),
                imageVector = Icons.Outlined.Swipe,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}


@Composable
fun ApartmentImage(url: String) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(500)
        }
    )
    val painterState = painter.state
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "Image",
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxSize()
        )
        if (painterState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ApartmentStatusBadge(apartmentStatus: ApartmentStatus) {
    RentlyApartmentCardTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(10.dp)
                .wrapContentSize(),
            color = apartmentStatus.backgroundColor,
            elevation = 5.dp,
        ) {
            Text(
                text = apartmentStatus.status,
                color = apartmentStatus.color,
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun DeleteApartmentBadge(apartment: Apartment, onDeleteApartment: (apartment: Apartment) -> Unit) {
    RentlyApartmentCardTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(30.dp)
                .width(30.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            FloatingActionButton(
                modifier = Modifier.wrapContentSize(),
                onClick = {onDeleteApartment(apartment)},
                shape = RoundedSquareShape.large,
                backgroundColor = Color.Red
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Apartment",
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun switchApartmentStatus(apartment: Apartment, onChangeApartmentStatus: (apartment: Apartment) -> Unit) {
    val checkedState = rememberSaveable { mutableStateOf(false) }

    var isEnabled = false
    if (apartment.status == ApartmentStatus.Available.status || apartment.status == ApartmentStatus.Closed.status) {
        isEnabled = true
    }
    if (apartment.status == ApartmentStatus.Available.status) {
        checkedState.value =true
    }

    RentlyApartmentCardTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Switch(
                enabled = isEnabled,
                checked = checkedState.value,
                onCheckedChange = {
                    onChangeApartmentStatus(apartment)
                    checkedState.value = it
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = ApartmentAvailableStatusColor,
                    uncheckedThumbColor = ApartmentClosedStatusColor,
                    checkedTrackColor = Color.DarkGray,
                    uncheckedTrackColor = Color.DarkGray,
                )
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ApartmentCardPreview() {
    val context = LocalContext.current
    var apartment = Apartment(
        _id = "test",
        city = "Tel-Aviv",
        price = 7800,
        numberOfRooms = 3,
        address = "Dov Hauzner 2",
        numberOfBaths = 1,
        numberOfBeds = 2,
        size = 54,
        imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1"
    )
    ApartmentCard(
        apartment = apartment,
        navController = NavController(context = context),
        onApartmentClick = {})
}