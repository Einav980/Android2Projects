package com.example.rently.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.model.User
import com.example.rently.ui.screens.login.LoginViewModel
import com.example.rently.ui.screens.register.RegisterViewModel
import com.example.rently.ui.screens.register.events.RegisterFormEvent
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.PhoneMaskTransformation

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    closeScreen: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state = viewModel.state

    var showErrorDialog by remember { mutableStateOf(false) }
    var showLoadingProgress by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is RegisterViewModel.ValidationEvent.RegisterError -> {
                    showLoadingProgress = false
                    showErrorDialog = true
                }

                is RegisterViewModel.ValidationEvent.RegisterLoading -> {
                    showLoadingProgress = true
                }

                is RegisterViewModel.ValidationEvent.RegisterSuccess -> {
                    showLoadingProgress = false
                    showSuccessDialog = true
                }
            }
        }
    }

    RentlyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            // Head
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 100.dp))
                    .background(MaterialTheme.colors.primary)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Sign up",
                        style = MaterialTheme.typography.h2,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextButton(
                        onClick = { closeScreen() },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.White
                        )
                        Text(
                            text = "Back",
                            color = Color.White
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(weight = 7f)
                    .padding(start = 30.dp, end = 30.dp, top = 15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        onValueChange = {
                            viewModel.onEvent(RegisterFormEvent.EmailChanged(it))
                        },
                        label = {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        isError = state.emailError != null,
                        singleLine = true,
                    )
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        onValueChange = {
                            viewModel.onEvent(RegisterFormEvent.PasswordChanged(it))
                        },
                        label = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = state.passwordError != null,
                    )
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.firstName,
                        label = {
                            Text(
                                text = "First Name",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        onValueChange = {
                            viewModel.onEvent(RegisterFormEvent.FirstNameChanged(it))
                        },
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
                        modifier = Modifier.fillMaxWidth(),
                        value = state.lastName,
                        label = {
                            Text(
                                text = "Last Name",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        onValueChange = {
                            viewModel.onEvent(RegisterFormEvent.LastNameChanged(it))
                        },
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
                        modifier = Modifier.fillMaxWidth(),
                        value = state.phone,
                        label = {
                            Text(
                                text = "Phone",
                                style = MaterialTheme.typography.subtitle2
                            )
                        },
                        onValueChange = {
                            viewModel.onEvent(RegisterFormEvent.PhoneChanged(it))
                        },
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
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(RegisterFormEvent.Register)
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)),
                    ) {
                        if (showLoadingProgress) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text(
                                text = "Create Account",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { /* DO NOTHING */ },
                buttons = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = {
                            showSuccessDialog = false
                            onRegisterSuccess()
                        }) {
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
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Success",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "You have been registered successfully!",
                        textAlign = TextAlign.Center
                    )
                })
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
                        text = state.registerErrorMessage ?: "",
                        textAlign = TextAlign.Center
                    )
                }
            )
        }

    }
}
//
//@ExperimentalAnimationApi
//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    val viewModel: RegisterViewModel = hiltViewModel()
//    RegisterScreen(viewModel = viewModel, closeScreen = {}, onSignUpSuccessful = {})
//}
