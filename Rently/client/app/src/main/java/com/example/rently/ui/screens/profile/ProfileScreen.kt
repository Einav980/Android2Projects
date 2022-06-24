package com.example.rently.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.OutlinedChip
import com.example.rently.ui.components.Section
import com.example.rently.ui.screens.profile.ProfileScreenViewModel
import com.example.rently.ui.theme.*
import com.example.rently.util.PhoneMaskTransformation
import com.example.rently.validation.presentation.ProfileFormEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

    var isPhoneError by remember {
        mutableStateOf(false)
    }
    var isFirstNameError by remember {
        mutableStateOf(false)
    }
    var isLastNameError by remember {
        mutableStateOf(false)
    }

    //retrieve logged in user information
    viewModel.getLoggedInUser()
    viewModel.getLoggedInUserApartments()
    viewModel.getLoggedInUserWatchlist()

    RentlyTheme() {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (viewModel.isUserHeadNotEmpty()) {
                        UserHead(
                            firstName = viewModel.headFirstname.value,
                            lastName = viewModel.headLastname.value,
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
                    Text(
                        text = "${viewModel.firstname.value} ${viewModel.lastname.value}",
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        fontSize = 20.sp
                    )
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
                            "My Apartments: ${viewModel.myApartments.value}",
                            "Watch list: ${viewModel.myWatchlist.value}"
                        )
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight =5f, fill = false),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = viewModel.firstname.value,
                        onValueChange = {
                            viewModel.firstname.value = it
                            viewModel.clearFirstNameError()
                            isFirstNameError = false
                        },
                        label = { Text(text = "First Name", style = MaterialTheme.typography.subtitle2) },
                        enabled = viewModel.editableText.value,
                        singleLine = true,
                        isError = isFirstNameError,
                        trailingIcon = {
                            if(isFirstNameError){
                                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                            }
                        }
                    )
                    if(! viewModel.isFirstNameValid.value){
                        isFirstNameError = true
                        Text(
                            text = "First name could not be empty",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = viewModel.lastname.value,
                        onValueChange = {
                            viewModel.lastname.value = it
                            viewModel.clearLastNameError()
                            isLastNameError = false
                        },
                        label = { Text(text = "Last Name", style = MaterialTheme.typography.subtitle2) },
                        enabled = viewModel.editableText.value,
                        singleLine = true,
                        isError = isLastNameError,
                        trailingIcon = {
                            if(isLastNameError){
                                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                            }
                        }
                    )
                    if(! viewModel.isLastNameValid.value){
                        isLastNameError = true
                        Text(
                            text = "Last name could not be empty",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = viewModel.email.value,
                        onValueChange = {
                            viewModel.email.value = it
                        },
                        label = { Text(text = "Email", style = MaterialTheme.typography.subtitle2) },
                        enabled = false,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = viewModel.phone.value,
                        onValueChange = {
                            viewModel.phone.value = it
                            viewModel.clearPhoneError()
                            isPhoneError = false
                        },
                        label = { Text(text = "Phone", style = MaterialTheme.typography.subtitle2) },
                        enabled = viewModel.editableText.value,
                        visualTransformation = PhoneMaskTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = isPhoneError,
                        trailingIcon = {
                            if(isPhoneError){
                                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                            }
                        }
                    )
                    if(! viewModel.isPhoneValid.value){
                        isPhoneError = true
                        Text(
                            text = "Phone number is invalid",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    shape = RoundedSquareShape.large,
                    onClick = {
                        viewModel.onEvent(ProfileFormEvent.Logout)
                    },
                    enabled = !viewModel.editableText.value
                ) {
                    Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Logout")
                    Text(text = "Logout")
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
                onClick = { viewModel.editTextFields() },
                shape = RoundedSquareShape.large,
                backgroundColor = Color.White,
                modifier = Modifier
                    .border(0.5.dp, MaterialTheme.colors.primary)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = if (!viewModel.editableText.value) Icons.Filled.Edit else Icons.Filled.Save,
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colors.primary,
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
    size: Dp = 120.dp,
    textStyle: TextStyle = MaterialTheme.typography.h2,
) {
    Box(
        modifier = modifier
            .border(1.dp,Color.White , shape = CircleShape )
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${firstName[0].uppercaseChar()}${lastName[0].uppercaseChar()}",
            style = textStyle,
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
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow {
            items(items) {
                OutlinedChip(
                    text = it
                )
            }
        }
    }
}

