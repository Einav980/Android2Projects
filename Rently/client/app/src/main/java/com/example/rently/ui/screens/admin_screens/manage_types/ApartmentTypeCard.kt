package com.example.rently.ui.screens.admin_screens.manage_types

import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rently.model.ApartmentType
import com.example.rently.ui.theme.RentlyTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import kotlin.math.roundToInt

@Composable
fun ApartmentTypeCard(apartmentType: ApartmentType, onDeleteSwiped: (id: String) -> Unit) {
    RentlyTheme {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(2.dp),
            elevation = 10.dp
        ) {
            val delete = SwipeAction(
                onSwipe = {
                    onDeleteSwiped(apartmentType._id)

                },
                icon = {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                },
                background = MaterialTheme.colors.primary
            )
            SwipeableActionsBox(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                startActions = listOf(delete),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .fillMaxWidth()
                            .weight(4f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            style= TextStyle(fontSize = 28.sp),
                            text = apartmentType.type,
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .fillMaxWidth()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(start = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    )
                    {
                        Text(
                            text = "Id: ${apartmentType._id}",
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApartmentTypeCardPreview() {
    val apartmentType = ApartmentType(type = "Normal")
    ApartmentTypeCard(apartmentType = apartmentType, onDeleteSwiped = {})
}