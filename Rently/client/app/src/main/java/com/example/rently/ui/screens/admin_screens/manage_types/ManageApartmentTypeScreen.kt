package com.example.rently.ui.screens.admin_screens.manage_types

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.theme.RentlyTheme

@Composable
fun ManageApartmentTypeScreen(
    viewModel: ManageApartmentTypeViewModel = hiltViewModel(),
    onAddClicked: (typeName: String) -> Unit
) {
    val context = LocalContext.current
    var typeName by remember { mutableStateOf("") }
    RentlyTheme {

        viewModel.listApartmentTypes()
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(20.dp),
            ) {
                TextField(
                    modifier = Modifier.weight(3f),
                    value = typeName,
                    onValueChange = { typeName = it },
                    label = { Text("Type Name") },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.width(15.dp))
                Button(
                    onClick = {
//                        onAddClicked(typeName)
                        Toast.makeText(context, "This is a text", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Text("Add")
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(9f)
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

@Preview(showBackground = true)
@Composable
fun ManageApartmentTypeScreenPreview() {
    ManageApartmentTypeScreen(onAddClicked = {})
}