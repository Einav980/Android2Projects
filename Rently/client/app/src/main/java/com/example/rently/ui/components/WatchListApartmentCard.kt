package com.example.rently.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.rently.model.Apartment
import com.example.rently.ui.theme.RentlyApartmentCardTheme
import com.example.rently.ui.theme.RentlySubtitleTextColor
import com.example.rently.util.Constants
import java.text.NumberFormat
import java.util.*

@Composable
fun WatchListApartmentCard(
    apartment: Apartment,
    onApartmentClick: (apartment: Apartment) -> Unit,
    onRemoveApartment: (apartment: Apartment) -> Unit = {},
) {

    RentlyApartmentCardTheme {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth()
                .height(150.dp)
                .clickable {
                    onApartmentClick(apartment)
                },
            contentAlignment = Alignment.CenterStart
        )
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(10.dp),
                elevation = 10.dp,
                shape = MaterialTheme.shapes.large
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxWidth()
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f)
                                .padding(start = 25.dp, end = 0.dp, top = 8.dp, bottom = 3.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = apartment.address,
                                modifier = Modifier.weight(2f),
                                style = MaterialTheme.typography.subtitle2,
                                color = RentlySubtitleTextColor
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 3.dp, bottom = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

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
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
//                        verticalArrangement = Arrangement.Center
                    ) {
                        WatchListDeleteApartmentBadge(apartment, onRemoveApartment)

                        Spacer(modifier = Modifier
                            .height(10.dp)
                            .weight(2f))

                        val apartmentCardColor =MaterialTheme.colors.primary
                        val format = NumberFormat.getCurrencyInstance()
                        format.maximumFractionDigits = 0
                        format.currency = Currency.getInstance("ILS")
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp, 0.dp, 0.dp, 0.dp))
                                .background(apartmentCardColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = format.format(apartment.price),
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.White,
                                letterSpacing = 1.1.sp
                            )
                        }
                    }
                }
            }
            WatchListApartmentImage(url = apartment.imageUrl)
        }
    }
}




@Composable
fun WatchListApartmentImage(url: String) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(500)
        }
    )
    val painterState = painter.state
    Card(shape = CircleShape, elevation = 10.dp, modifier = Modifier.padding(3.dp)) {
        Box(
            modifier = Modifier
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        )
        {
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = "Image",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
            )
            if (painterState is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
fun WatchListDeleteApartmentBadge(apartment: Apartment, onDeleteApartment: (apartment: Apartment) -> Unit) {
    RentlyApartmentCardTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .width(15.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            FloatingActionButton(
                modifier = Modifier.wrapContentSize(),
                onClick = {onDeleteApartment(apartment)},
                shape = RoundedCornerShape(0.dp, 0.dp, 0.dp , 15.dp),
                backgroundColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Delete Apartment",
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun WatchListApartmentCardPreview() {
    WatchListApartmentCard(
        apartment = Constants.apartment,
        onApartmentClick = {})
}