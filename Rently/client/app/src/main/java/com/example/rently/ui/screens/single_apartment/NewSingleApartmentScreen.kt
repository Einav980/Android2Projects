package com.example.rently.ui.screens.single_apartment

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.rently.model.Apartment
import com.example.rently.ui.components.SquareChip
import com.example.rently.ui.components.StarsChip
import com.example.rently.ui.theme.RentlyGrayColor
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.ui.theme.RoundedSquareShape
import com.example.rently.util.Constants
import java.text.NumberFormat
import java.util.*

@Composable
fun NewSingleApartmentScreen(apartment: Apartment) {

    val context = LocalContext.current

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
                            text = apartment.address,
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
                                text = apartment.city,
                                style = MaterialTheme.typography.h5,
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
                        Text(
                            text = format.format(apartment.price),
                            fontSize = MaterialTheme.typography.h5.fontSize
                        )
                        StarsChip(4.3, shape = RoundedSquareShape.large)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SquareChip(
                            icon = Icons.Outlined.Bathtub,
                            foregroundColor = MaterialTheme.colors.primary,
                            text = "1 Bath"
                        )
                        SquareChip(
                            icon = Icons.Outlined.Kitchen,
                            foregroundColor = MaterialTheme.colors.primary,
                            text = "1 Kitchen"
                        )
                        SquareChip(
                            icon = Icons.Outlined.Bed,
                            foregroundColor = MaterialTheme.colors.primary,
                            text = "2 Beds"
                        )
                        SquareChip(
                            icon = Icons.Outlined.Stairs,
                            foregroundColor = MaterialTheme.colors.primary,
                            text = "Floor 5"
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(3f)
                        .verticalScroll(rememberScrollState())
                )
                {
                    Text(
                        text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.",
                        lineHeight = 35.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        elevation = ButtonDefaults.elevation(defaultElevation = 5.dp),
                        onClick = { Toast.makeText(context, "Get contact inforamtion", Toast.LENGTH_SHORT).show() },
                        shape = RoundedSquareShape.large
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Contact Information"
                        )
                        Text(
                            "Contact Information",
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
                onClick = { Toast.makeText(context, "See on map clicked", Toast.LENGTH_SHORT).show() },
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
            Button(
                modifier = Modifier.alpha(0.6f),
                onClick = { Toast.makeText(context, "Back clicked", Toast.LENGTH_SHORT).show() },
                shape = RoundedSquareShape.large,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewSingleApartmentScreenPreview() {
    val apartment = Constants.apartment
    RentlyTheme() {
        NewSingleApartmentScreen(apartment = apartment)
    }
}