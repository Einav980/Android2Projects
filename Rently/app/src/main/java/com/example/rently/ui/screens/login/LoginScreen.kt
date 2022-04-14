package com.example.rently.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.ui.theme.RentlyColors
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
    MaterialTheme(colors = RentlyColors, typography = RentlyTypography) {
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
            )
            {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email Icon"
                        )
                    },
                    isError = viewModel.isEmailValid.value,
                )
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val user = User(email = email, password = password)
                        loginUser(viewModel = viewModel, user = user)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp)),
                    contentPadding = PaddingValues(15.dp),
                    shape = MaterialTheme.shapes.large
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
                        color = MaterialTheme.colors.primaryVariant
                    )
                }
                if (viewModel.showInvalidCredentialsDialog.value) {
                    MissingValuesDialog(viewModel = viewModel)
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
//    LoginScreen(rememberNavController(), )
//}

@Composable
fun MissingValuesDialog(viewModel: LoginViewModel) {
    Dialog(
        onDismissRequest = { viewModel.showInvalidCredentialsDialog.value = false },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .padding(10.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Email and password must not be empty!",
                color = MaterialTheme.colors.primary
            )
        }
    }
}

fun loginUser(viewModel: LoginViewModel, user: User) {
//    if(! validateEmail(viewModel, user.email)){
//    }
    viewModel.loginUser(user = user)
}

fun validateEmail(viewModel: LoginViewModel, email: String): Boolean{
    if(email.matches(Regex(".*@.*..com"))){
        viewModel.isEmailValid.value = true
        return true
    }
    else{
        viewModel.isEmailValid.value = false
        return false
    }
}