package com.example.rently.ui.screens.admin_screens.manage_types

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.components.RentlyTextField
import com.example.rently.ui.theme.RentlyTheme

@Composable
fun ManageApartmentTypeScreen(
    viewModel: ManageApartmentTypeViewModel = hiltViewModel(),
//    onAddClicked: (typeName: String) -> Unit
) {
    val context = LocalContext.current
    var typeName by remember { mutableStateOf("") }
    // Retrieve apartment types
    viewModel.listApartmentTypes()
    RentlyTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(MaterialTheme.shapes.large),
                    value = typeName,
                    onValueChange = { typeName = it },
                    label = { Text(
                        style = TextStyle(fontSize = 22.sp),
                        text = "Type Name"
                    ) },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 22.sp),
                )
                Spacer(modifier = Modifier.width(15.dp))
                Button(
                    modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(0.dp, 10.dp, 10.dp, 10.dp)
                    .clip(MaterialTheme.shapes.large),
                    onClick = {
                        if (typeName.isNotEmpty()) {
                            Log.d("Rently", typeName)
                            viewModel.addApartmentType(typeName)
                            typeName = ""
                        } else {
                            Toast.makeText(context, "Type name cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                        }},
                ) {
                    Text(
                        style = TextStyle(fontSize = 22.sp),
                        text = "Add")
                }
            }
            Column(
                modifier = Modifier
                    .weight(9f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LaunchedEffect(key1 = Unit) {
                    Log.d("Rently", viewModel.isLoading.value.toString())
                }
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(15.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(items = viewModel.apartmentTypes.value) { apartmentType ->
                            ApartmentTypeCard(
                                apartmentType = apartmentType,
                                onDeleteSwiped = { viewModel.deleteApartmentType(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageApartmentTypeScreenPreview() {
    ManageApartmentTypeScreen(/*onAddClicked = {}*/)
}