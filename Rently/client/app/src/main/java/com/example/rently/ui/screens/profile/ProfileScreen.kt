package com.example.rently.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.screens.profile.ProfileScreenViewModel
import com.example.rently.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val editableText = false
    //retrieve logged in user
    viewModel.getLoggedInUser()
    val user = remember { viewModel.user.value }

    RentlyTheme() {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black)
                    .background(MaterialTheme.colors.primary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if(viewModel.isUserNotEmpty()){
                        UserHead(
                            firstName = viewModel.firstname.value,
                            lastName = viewModel.lastname.value,
                            Modifier.size(100.dp)
                        )
                    } else{
                        UserHead(
                            firstName = "einav",
                            lastName = "malcka",
                            Modifier.size(100.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${user.firstName} ${user.lastName}")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ChipGroup(
                        items = listOf(
                            "My Apartments: 2",
                            "Favorites: 50"
                        )
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .weight(5f)
                ) {
                    TextInfoRow("First name", viewModel.firstname.value , editableText, {viewModel.onFirstNameChange(it)})
                    TextInfoRow("Last name", viewModel.lastname.value, editableText, {viewModel.onLastNameChange(it)})
                    TextInfoRow("Email", viewModel.email.value, editableText, {viewModel.onEmailChange(it)})
                    TextInfoRow("Phone", viewModel.phone.value, editableText, {viewModel.onPhoneChange(it)})
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    shape = RoundedSquareShape.large
                ) {
                    Text(
                        "Logout",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(8.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
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
                onClick = { }, // TODO
                shape = RoundedSquareShape.large,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun UserHead(
    firstName: String,
    lastName: String,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    textStyle: TextStyle = MaterialTheme.typography.h3,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primaryVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${firstName[0].uppercaseChar()}${lastName[0].uppercaseChar()}",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            color = Color.White,
            letterSpacing = 5.sp
        )
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
                com.example.rently.ui.components.Chip(
                    name = it,
                    textStyle = MaterialTheme.typography.body1,
                    padding = PaddingValues(start = 18.dp, end = 18.dp, top = 5.dp, bottom = 5.dp)
                )
            }
        }
    }
}

@Composable
fun TextInfoRow(lable: String, value: String, editable: Boolean, onValueChange: (String) -> Unit) {
//    var text by remember { mutableStateOf(value) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            enabled = editable,
            value = value,
            onValueChange = { onValueChange },
            label = { Text(lable) }
        )
    }
}

