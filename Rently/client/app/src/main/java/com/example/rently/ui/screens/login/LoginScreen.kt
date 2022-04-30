package com.example.rently.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.model.User
import com.example.rently.ui.theme.RentlyLightColors
import com.example.rently.ui.theme.RentlyTypography
import com.example.rently.util.Constants

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpClicked: () -> Unit,
    onLoginSuccessful: () -> Unit
) {
    if (viewModel.isLoggedIn.value) {
        LaunchedEffect(key1 = Unit) {
            onLoginSuccessful()
        }
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
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
            ){
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    horizontalAlignment = Alignment.Start
                ){
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Email", style = MaterialTheme.typography.subtitle2) },
                        singleLine = true,
                        isError = !viewModel.isValidEmail.value,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Email Icon"
                            )
                        },
                        trailingIcon = {
                            if (!viewModel.isValidEmail.value) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = "error",
                                    tint = MaterialTheme.colors.error
                                )
                            }
                        }
                    )
                    if (!viewModel.isValidEmail.value) {
                        Text(
                            text = "Invalid email address",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                            viewModel.isValidPassword.value = true
                        },
                        label = { Text(text = "Password", style = MaterialTheme.typography.subtitle2) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = {
                            Icon(Icons.Filled.Lock, "Lock Icon")
                        },
                        isError = !viewModel.isValidPassword.value,
                        trailingIcon = {
                            if (!viewModel.isValidPassword.value) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = "error",
                                    tint = MaterialTheme.colors.error
                                )
                            }
                        }
                    )
                    if (!viewModel.isValidPassword.value) {
                        Text(
                            text = "Password cannot be empty",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val user = User(email = email, password = password)
                        loginUser(viewModel = viewModel, user = user)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp)),
                    contentPadding = PaddingValues(15.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    if (viewModel.isLoading.value) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(
                            text = "Sign in",
                            color = Color.White,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (viewModel.invalidCredentials.value) {
                    Text(
                        text = "Invalid credentials",
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4
                    )
                }
                if (viewModel.errorOccurred.value) {
                    Text(
                        text = "Network Error",
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4
                    )
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
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(rememberNavController())
//}

fun loginUser(viewModel: LoginViewModel, user: User) {
    viewModel.loginUser(user = user)
}
