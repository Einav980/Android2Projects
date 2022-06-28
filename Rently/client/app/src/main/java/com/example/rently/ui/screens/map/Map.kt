package com.example.rently.ui.screens.map

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.SharedViewModel
import com.example.rently.model.Apartment
import com.example.rently.ui.components.ApartmentImage
import com.example.rently.ui.components.BackButton
import com.example.rently.ui.screens.map.events.MapEvent
import com.example.rently.ui.theme.RoundedSquareShape
import com.example.rently.util.Constants
import com.example.rently.util.priceToCurrency
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun Map(
    viewModel: MapViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxSize(),
    latLng: LatLng? = null,
    onApartmentClicked: (Apartment) -> Unit,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state
    val cameraPositionState = rememberCameraPositionState {
        if (latLng != null) {
            CameraPosition.fromLatLngZoom(latLng, 15f)
        } else {
            state.cameraPosition
        }
    }

    var showLoading by remember {
        mutableStateOf(false)
    }

    var showSelectedApartment by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is MapViewModel.ValidationEvent.LoadingApartments -> {
                    showLoading = true
                }

                is MapViewModel.ValidationEvent.LoadingFinished -> {
                    showLoading = false
                }

                is MapViewModel.ValidationEvent.ShowSelectedApartment -> {
                    showSelectedApartment = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = state.properties,
            uiSettings = state.staticMapUiSettings,
        ) {
            state.apartments.forEach { apartment ->
                Marker(
                    state = MarkerState(apartment.location),
                    title = apartment.status,
                    onClick = {
                        viewModel.onEvent(MapEvent.SelectedApartmentChanged(apartment))
                        false
                    }
                )
            }
        }
        if (showSelectedApartment && state.selectedApartment != null) {
            SelectedApartmentCard(
                apartment = state.selectedApartment,
                onSelectedApartmentClicked = onApartmentClicked,
                onSelectedApartmentClosed = { showSelectedApartment = false })
        }
    }
    if (showLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        BackButton(onClick = onBackClicked)
    }
}

@Composable
fun SelectedApartmentCard(
    apartment: Apartment,
    onSelectedApartmentClosed: () -> Unit,
    onSelectedApartmentClicked: (Apartment) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedSquareShape.large)
            .background(Color.Red)
            .animateContentSize(animationSpec = tween(300))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    contentAlignment = Alignment.BottomStart
                ) {
                    ApartmentImage(url = apartment.imageUrl, shape = RoundedSquareShape.large)
                    Card(
                        modifier = Modifier.padding(8.dp),
                        shape = RoundedSquareShape.large,
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "${priceToCurrency(apartment.price)} / ",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "mon",
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(6f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = apartment.address,
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp),
                            onClick = { onSelectedApartmentClicked(apartment) },
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White
                            ),
                            shape = RoundedSquareShape.large
                        ) {
                            Text(
                                text = "More",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                IconButton(
                    onClick = onSelectedApartmentClosed, modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}