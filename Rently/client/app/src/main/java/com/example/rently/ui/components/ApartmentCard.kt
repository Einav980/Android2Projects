package com.example.rently.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.rently.R
import com.example.rently.model.Apartment
import com.example.rently.ui.theme.RentlySecondaryVariantColor
import com.example.rently.ui.theme.RentlySubtitleTextColor
import okhttp3.internal.format
import java.text.NumberFormat
import java.util.*

@Composable
fun ApartmentCard(apartment: Apartment){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = 10.dp,
        shape = MaterialTheme.shapes.large
    ){
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Box(modifier = Modifier
                .weight(6f)
                .fillMaxWidth()
            ){
                ApartmentImage(url = apartment.imageUrl)
            }

            Row(modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
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
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = apartment.address,
                    style = MaterialTheme.typography.h6,
                    color = RentlySecondaryVariantColor
                )
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                val format = NumberFormat.getCurrencyInstance()
                format.maximumFractionDigits = 0
                format.currency = Currency.getInstance("ILS")
                Text(
                    text = format.format(apartment.price),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primary,
                    letterSpacing = 1.1.sp
                )
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
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth()
        )
        if(painterState is AsyncImagePainter.State.Loading){
            CircularProgressIndicator()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ApartmentCardPreview(){
    var apartment = Apartment("Tel-Aviv", price = 7800, numberOfRooms = 3, address = "Dov Hauzner 2", numberOfBaths = 1, numberOfBeds = 2, size = 54, type = "Private", imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1")
    ApartmentCard(apartment = apartment)
}