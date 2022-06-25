package com.example.rently.ui.screens.profile

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.components.OutlinedChip
import com.example.rently.ui.screens.profile.events.ProfileFormEvent
import com.example.rently.ui.theme.*
import com.example.rently.util.PhoneMaskTransformation

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = hiltViewModel(), onLogout: () -> Unit) {
    val scrollState = rememberScrollState()


    val context = LocalContext.current
    val state = viewModel.state

    var showErrorDialog by remember { mutableStateOf(false) }
    var showLoadingProgress by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ProfileScreenViewModel.ValidationEvent.EditError -> {
                    showLoadingProgress = false
                    showErrorDialog = true
                }

                is ProfileScreenViewModel.ValidationEvent.EditLoading -> {
                    showLoadingProgress = true
                }

                is ProfileScreenViewModel.ValidationEvent.EditSuccess -> {
                    showLoadingProgress = false
                }
            }
        }
    }

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
                            firstName = state.headFirstname,
                            lastName = state.headLastname,
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
                        text = "${state.firstName} ${state.lastName}",
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
                            "My Apartments: ${state.userApartments}",
                            "Watch list: ${state.userWatchlist}"
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
                        .weight(weight = 5f, fill = false),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = state.firstName,
                        onValueChange = {
                            viewModel.onEvent(ProfileFormEvent.FirstNameChanged(it))
                        },
                        label = {
                            Text(
                                text = "First Name",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        enabled = state.editableText,
                        singleLine = true,
                        isError = state.firstNameError != null,
                    )
                    if (state.firstNameError != null) {
                        Text(
                            text = state.firstNameError,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = state.lastName,
                        onValueChange = {
                            viewModel.onEvent(ProfileFormEvent.LastNameChanged(it))
                        },
                        label = {
                            Text(
                                text = "Last Name",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        enabled = state.editableText,
                        singleLine = true,
                        isError = state.lastNameError != null,
                    )
                    if (state.lastNameError != null) {
                        Text(
                            text = state.lastNameError,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = state.userEmail,
                        onValueChange = {},
                        label = {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        enabled = false,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = {
                            viewModel.onEvent(ProfileFormEvent.PhoneChanged(it))
                        },
                        label = {
                            Text(
                                text = "Phone",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        enabled = state.editableText,
                        visualTransformation = PhoneMaskTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = state.phoneError != null,
                    )
                    if (state.phoneError != null) {
                        Text(
                            text = state.phoneError,
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
                        onLogout()
                    },
                    enabled = !state.editableText
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
                onClick = { viewModel.onEvent(ProfileFormEvent.EditProfile) },
                shape = RoundedSquareShape.large,
                backgroundColor = Color.White,
                modifier = Modifier
                    .border(0.5.dp, MaterialTheme.colors.primary)
                    .size(40.dp)
            ) {
                if (showLoadingProgress) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        imageVector = if (!state.editableText) Icons.Filled.Edit else Icons.Filled.Save,
                        contentDescription = "Edit Profile",
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
    }


    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { /* DO NOTHING */ },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                }
            },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Error",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Could not change user info",
                    textAlign = TextAlign.Center
                )
            }
        )
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
            .border(1.dp, Color.White, shape = CircleShape)
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

