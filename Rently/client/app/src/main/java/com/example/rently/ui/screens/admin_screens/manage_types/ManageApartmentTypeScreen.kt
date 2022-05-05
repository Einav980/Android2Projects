package com.example.rently.ui.screens.admin_screens.manage_types

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rently.ui.theme.RentlyTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun ManageApartmentTypeScreen() {
    var typeName by remember { mutableStateOf("") }
    RentlyTheme {

        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = typeName,
                    onValueChange = { typeName = it },
                    label = { Text("Type Name") })
            }
            Column(modifier = Modifier.weight(8f)) {
                val archive = SwipeAction(
                    onSwipe = {
                        Log.d("Rently", "Swiped")
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.padding(16.dp),
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    },
                    background = Color.Green
                )

                SwipeableActionsBox(
                    startActions = listOf(archive),
                    modifier = Modifier.background(Color.Red)
                ) {
                    Text(
                        "hello", modifier = Modifier
                            .fillMaxWidth()
                    )
//                        Box(
//                            Modifier
//                                .clip(RoundedCornerShape(size = 50.dp))
//                                .background(MaterialTheme.colors.primary)
//                                .size(50.dp)
//                        )
//                        Spacer(Modifier.width(16.dp))
//                        Column(modifier = Modifier.weight(7f)) {
//                            Text(
//                                text = "Title",
//                                style = TextStyle(
//                                    fontSize = MaterialTheme.typography.h5.fontSize,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                            Text(text = "Some Random text.")
//
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageApartmentTypeScreenPreview() {
    ManageApartmentTypeScreen()
}