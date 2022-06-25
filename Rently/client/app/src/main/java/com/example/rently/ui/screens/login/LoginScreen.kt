package com.example.rently.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.components.RentlyTextField
import com.example.rently.ui.theme.RentlyLightColors
import com.example.rently.ui.theme.RentlyTypography
import com.example.rently.util.Constants
import com.example.rently.ui.screens.login.events.LoginFormEvent

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpClicked: () -> Unit,
    onLoginSuccessful: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state

    var showErrorDialog by remember { mutableStateOf(false) }
    var showLoadingProgress by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.LoginError -> {
                    showLoadingProgress = false
                    showErrorDialog = true
                }

                is LoginViewModel.ValidationEvent.LoginLoading -> {
                    showLoadingProgress = true
                }

                is LoginViewModel.ValidationEvent.LoginSuccess -> {
                    onLoginSuccessful()
                }
            }
        }
    }

    MaterialTheme(colors = RentlyLightColors, typography = RentlyTypography) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = Constants.APP_TITLE,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f)
                    .fillMaxWidth()
                    .padding(PaddingValues(20.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    RentlyTextField(
                        value = state.email,
                        onValueChange = {
                            viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                        },
                        label = "Email",
                        isError = state.emailError != null,
                        errorMessage = state.emailError ?: "",
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Email"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    RentlyTextField(
                        value = state.password,
                        onValueChange = {
                            viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                        },
                        label = "Password",
                        isError = state.passwordError != null,
                        errorMessage = state.passwordError ?: "",
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Password"
                            )
                        },
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { viewModel.onEvent(LoginFormEvent.Login) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp)),
                    contentPadding = PaddingValues(15.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    if (showLoadingProgress) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(
                            text = "Sign in",
                            color = Color.White,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .padding(PaddingValues(30.dp))
                        .fillMaxHeight()
                ) {
                    TextButton(onClick = { onSignUpClicked() }) {
                        Text(
                            text = "Sign Up",
                            style = MaterialTheme.typography.h6
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
                        text = state.loginErrorMessage?: "",
                        textAlign = TextAlign.Center
                    )
                })
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(rememberNavController())
//}
