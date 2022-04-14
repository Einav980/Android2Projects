package com.example.rently.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.ui.screens.register.SignUpViewModel
import com.example.rently.ui.theme.RentlyColors
import com.example.rently.ui.theme.RentlyTypography
import com.example.rently.util.PhoneMaskTransformation

@Composable
fun PhonePrefixDropDown() {
    var expanded by remember { mutableStateOf(false)}
    val prefixes = listOf("050", "051", "052", "053", "054", "055", "056", "057", "058", "059")
    var selectedItem by remember { mutableStateOf("")}
    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded) { Icons.Filled.KeyboardArrowUp} else {Icons.Filled.KeyboardArrowDown}

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = {Text("Label")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
            prefixes.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedItem = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    MaterialTheme (colors = RentlyColors, typography = RentlyTypography){
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
            ){
                TextButton(
                    onClick = { navController.popBackStack() },
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
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    label = { Text(text = "Email") },
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = phone,
                    label = {Text(text = "Phone")},
                    onValueChange = { phone = it },
                    visualTransformation = PhoneMaskTransformation()
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        val user = User(email = email, password = password, phone = phone)
                        registerUser(user, viewModel = viewModel)
                              },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    if(viewModel.isLoading.value){
                        CircularProgressIndicator(color = Color.White)
                    }
                    else{
                        Text(
                            text = "Create Account",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary)
                                .padding(5.dp)
                        )
                    }
                    if(viewModel.registeredSuccessfully.value){
                        LaunchedEffect(key1 = Unit){
                            navController.navigate(Screen.Home.route)
                        }
                    }
                }
            }
        }
    }
}

fun registerUser(user: User, viewModel: SignUpViewModel){
    viewModel.registerUser(user)
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(rememberNavController())
}