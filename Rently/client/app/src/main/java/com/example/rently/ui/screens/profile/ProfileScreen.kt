package com.example.rently.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .border(1.dp, Color.Black)
        ) {
            Scaffold(
                topBar = {/**/ },
                drawerContent = {/**/ },
                bottomBar = {/**/ },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /*TODO*/ },

                        ) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                    }
                },
                snackbarHost = {/**/ },
                content = {
                    Text("Hello")
                }
            )
        }
        Column(modifier = Modifier
            .weight(3f)
            .fillMaxWidth()
            .border(1.dp, Color.Black)
        ) {

        }
        Column(modifier = Modifier
            .weight(3f)
            .fillMaxWidth()
            .border(1.dp, Color.Black)
        ) {

        }
    }
}