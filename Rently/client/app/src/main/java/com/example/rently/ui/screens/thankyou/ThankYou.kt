package com.example.rently.ui.screens.thankyou

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.ui.theme.RentlyTheme

@Composable
fun ThankYou(onLanded : () -> Unit, viewModel: ThankYouViewModel = hiltViewModel()) {
    RentlyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Thank you!", 
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = "Registered successfully!",
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
        viewModel.landed()
        if(viewModel.moveToLogin.value){
            LaunchedEffect(key1 = Unit){
                onLanded()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThankYouPreview() {
    ThankYou(hiltViewModel())
}