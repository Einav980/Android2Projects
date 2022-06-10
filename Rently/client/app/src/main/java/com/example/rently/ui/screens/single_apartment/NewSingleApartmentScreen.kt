package com.example.rently.ui.screens.single_apartment

import android.graphics.drawable.Icon
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Size
import com.example.rently.model.Apartment
import com.example.rently.ui.components.Chip
import com.example.rently.util.Constants
import java.text.NumberFormat
import java.util.*

@Composable
fun NewSingleApartmentScreen(apartment: Apartment) {

    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("ILS")

    val painter = rememberImagePainter(
        data = apartment.imageUrl,
        builder = {
            crossfade(500)
        }
    )
    val painterState = painter.state

    Column(modifier = Modifier.fillMaxSize()) {
        // Image and basic info section
        Box(
            modifier = Modifier
                .weight(4f)
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(text = apartment.address, fontSize = MaterialTheme.typography.h4.fontSize)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = apartment.city,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        color = Color.Gray
                    )
                }
            }
//            if (painterState is AsyncImagePainter.State.Loading) {
//                CircularProgressIndicator()
//            }
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
                    Text(
                        text = format.format(apartment.price),
                        fontSize = MaterialTheme.typography.h5.fontSize
                    )
                    Text(text = "5 Stars")
                }
                ChipGroup(
                    items = listOf(
                        "Floor ${apartment.floor}",
                        "${apartment.size} sqft",
                        "${apartment.numberOfBaths} Baths",
                        "${apartment.numberOfBeds} Beds",
                        "Good Location",
                        "Good Vibes"
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .weight(2f)
                    .verticalScroll(rememberScrollState())
            )
            {
                Text(
                    text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.",
                    lineHeight = 35.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(
                            "Book this apartment",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Button(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "SeeOnMap",
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChipGroup(
    items: List<String>,
) {
    Column(
        modifier = Modifier
            .padding(8.dp), verticalArrangement = Arrangement.Center
    ) {
        LazyRow {
            items(items) {
                Chip(
                    name = it,
                    textStyle = MaterialTheme.typography.body1,
                    padding = PaddingValues(start = 18.dp, end = 18.dp, top = 5.dp, bottom = 5.dp)
                )
            }
        }
    }
}

@Composable
fun SquareChip(
    icon: Icon,
    size: Size = Size(60, 60),
    color: Color = MaterialTheme.colors.primary,
    text: String
) {

}

@Preview(showBackground = true)
@Composable
fun NewSingleApartmentScreenPreview() {
    val apartment = Constants.apartment
    NewSingleApartmentScreen(apartment = apartment)
}