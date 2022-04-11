package com.example.rently.ui.screens

import android.util.Log
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rently.MainViewModel
import com.example.rently.MainViewModelFactory
import com.example.rently.UserViewModel
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.repository.Repository
import com.example.rently.repository.UserRepository
import com.example.rently.ui.screens.login.LoginViewModel
import com.example.rently.ui.theme.RentlyColors
import com.example.rently.ui.theme.RentlyTypography
import com.example.rently.util.Constants

@Composable
fun LoginScreen(navController: NavController) {
//        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
//        viewModel.listApartments()
//        viewModel.myResponse.observe(this, Observer { response ->
//            Log.d("Response", response.toString())
//            for(apartment in response){
//                Log.d("Response", apartment.toString())
//            }
//        })
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
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(3f),
                contentAlignment = Alignment.Center
            ){
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
                    onValueChange = {email = it},
                    label = { Text(text = "Email")},
                    leadingIcon =  {Icon(imageVector = Icons.Filled.Email, contentDescription = "Email Icon")}
                )
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = "Password")},
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {

                        val user = User(email = email, password = password)
                        Log.d("Login", user.toString())
//                        loginUser(viewModel = viewModel, user = user)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp)),
                    contentPadding = PaddingValues(15.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = "Sign in",
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .padding(PaddingValues(30.dp))
                        .fillMaxHeight()
                ){
                    TextButton(onClick = { navController.navigate(Screen.Signup.route) }) {
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

fun loginUser(viewModel: LoginViewModel, user:User){
    viewModel.loginUser(user = user)
}