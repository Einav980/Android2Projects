package com.example.rently.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.rently.navigation.Screen
import com.example.rently.ui.theme.RentlyApartmentCardTheme
import com.example.rently.ui.theme.RentlySecondaryVariantColor
import com.example.rently.ui.theme.RentlySubtitleTextColor
import com.example.rently.util.ApartmentStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

@Composable
fun ApartmentCard(
    navController: NavController,
    apartment: Apartment,
    myApartmentsList: Boolean = false,
    onApartmentClick: (apartment: Apartment) -> Unit
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
//                    ApartmentStatusBadge(apartmentStatus = apartment.status)
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
                        if (myApartmentsList) {
                            if (apartment.status != ApartmentStatus.AVAILABLE) {
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
                .padding(10.dp),
            color = apartmentStatus.backgroundColor,
            elevation = 5.dp
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
@Preview(showBackground = true)
fun ApartmentCardPreview() {
    val context = LocalContext.current
    var apartment = Apartment(
        "Tel-Aviv",
        price = 7800,
        numberOfRooms = 3,
        address = "Dov Hauzner 2",
        numberOfBaths = 1,
        numberOfBeds = 2,
        size = 54,
        imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1"
    )
    ApartmentCard(apartment = apartment, navController = NavController(context = context), onApartmentClick = {})
}