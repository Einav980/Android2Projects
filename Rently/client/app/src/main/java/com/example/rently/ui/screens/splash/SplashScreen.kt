package com.example.rently.ui.screens.splash

import android.window.SplashScreen
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rently.navigation.Screen
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.Constants.APP_TITLE
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = hiltViewModel(),
    onSplashEnds: (route: String) -> Unit
) {
    val context = LocalContext.current
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1250
        )
    )

    Splash(alpha = alphaAnim.value)
    LaunchedEffect(key1 = context){
        viewModel.validationEvents.collect { event ->
            when(event){
                is SplashScreenViewModel.ValidationEvent.LoggedIn -> {
                    onSplashEnds(Screen.MainPage.route)
                }

                is SplashScreenViewModel.ValidationEvent.NotLoggedIn -> {
                    onSplashEnds(Screen.Login.route)
                }
            }
        }
    }

}

@Composable
fun Splash(alpha: Float) {
    RentlyTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.alpha(alpha = alpha),
                    text = APP_TITLE,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primary
                )
                Icon(
                    modifier = Modifier
                        .size(120.dp)
                        .alpha(alpha = alpha),
                    imageVector = Icons.Default.Home,
                    contentDescription = "Rently Icon",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    Splash(1f)
}