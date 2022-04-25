package com.example.rently.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.ui.screens.register.SignUpViewModel
import com.example.rently.ui.theme.RentlyLightColors
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.ui.theme.RentlyTypography
import com.example.rently.util.PhoneMaskTransformation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
fun SignUpScreen(onSignUpSuccessful: () -> Unit, closeScreen: () -> Unit, viewModel: SignUpViewModel = hiltViewModel()) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    val invalidEmail by remember {
        mutableStateOf(false)
    }
    var isEmailError by remember {
        mutableStateOf(false)
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }
    var isPhoneError by remember {
        mutableStateOf(false)
    }
    RentlyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Head
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = { closeScreen() },
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Back",
                        color = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Sign up",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primary
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    horizontalAlignment = Alignment.Start
                ){

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            viewModel.clearEmailError()
                            isEmailError = false
                        },
                        label = { Text(text = "Email") },
                        isError = isEmailError,
                        enabled = ! viewModel.errorOccurred.value,
                        singleLine = true,
                        trailingIcon = {
                            if(isEmailError){
                                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                            }
                        }
                    )
                    if(viewModel.emailIsInUse.value){
                        isEmailError = true
                        Text(
                            text = "Email is in use",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    if(! viewModel.isEmailValid.value){
                        isEmailError = true
                        Text(
                            text = "Email is invalid",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            if(it.length >= 8 )
                            {
                                viewModel.clearPasswordError()
                                isPasswordError = false
                            }
                        },
                        label = { Text(text = "Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = isPasswordError,
                        enabled = ! viewModel.errorOccurred.value,
                        trailingIcon = {
                            if(isPasswordError){
                                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                            }
                        }
                    )
                    if(! viewModel.isPasswordValid.value){
                        isPasswordError = true
                        Text(
                            text = "Password must be at least 8 characters",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = phone,
                        label = { Text(text = "Phone") },
                        onValueChange = {
                            phone = it
                            viewModel.clearPhoneError()
                            isPhoneError = false
                                        },
                        visualTransformation = PhoneMaskTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = isPhoneError,
                        enabled = ! viewModel.errorOccurred.value,
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
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(
                        onClick = {
                            val user = User(email = email, password = password, phone = phone)
                            registerUser(user, viewModel = viewModel)
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)),
                        enabled = ! viewModel.errorOccurred.value
                    ) {
                        if (viewModel.isLoading.value) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text(
                                text = "Create Account",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                        if (viewModel.registeredSuccessfully.value) {
                            LaunchedEffect(key1 = Unit) {
                                onSignUpSuccessful()
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )
                    if(viewModel.errorOccurred.value)
                    {
                        Text(
                            text = "Network Error",
                            color = MaterialTheme.colors.primaryVariant,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}

fun registerUser(user: User, viewModel: SignUpViewModel) {
    viewModel.registerUser(user)
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val viewModel: SignUpViewModel = hiltViewModel()
    SignUpScreen(viewModel = viewModel, closeScreen = {}, onSignUpSuccessful = {})
}
