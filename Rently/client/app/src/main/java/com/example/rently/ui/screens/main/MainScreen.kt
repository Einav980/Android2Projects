package com.example.rently.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.House
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rently.SharedViewModel
import com.example.rently.navigation.*
import com.example.rently.ui.screens.main.MainScreenViewModel
import com.example.rently.ui.theme.RentlyCardShape
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.Constants
import com.example.rently.util.UserType


@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    onLogout: () -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onMapClicked: () -> Unit,
    onApartmentClicked: () -> Unit,
    onAddApartmentClicked: () -> Unit,
) {
    viewModel.getLoggedInUserPermissions()

    val navController = rememberNavController()

    RentlyTheme {
        Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController,
                    userType = viewModel.userType.value
                )
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavGraph(
                    sharedViewModel = sharedViewModel,
                    navController = navController,
                    onLogout = onLogout,
                    onApartmentClicked = onApartmentClicked,
                    onMapClicked = onMapClicked,
                    onAddApartmentClicked = onAddApartmentClicked,
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController, userType: UserType) {

    val screens = navListForUserType(userType)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
                label = screen.title
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavController,
    label: String
) {
    BottomNavigationItem(
        alwaysShowLabel = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        icon = {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        },
        label = { Text(text = label) }
    )
}

@Composable
fun navListForUserType(userType: UserType): List<BottomBarScreen> {
    if (userType.equals(UserType.Normal)) {
        return listOf(
            BottomBarScreen.Apartments,
            BottomBarScreen.ManageApartments,
            BottomBarScreen.Watchlist,
            BottomBarScreen.Profile
        )
    } else {
        return listOf(
            BottomBarScreen.Apartments,
            BottomBarScreen.AdminManageApartments,
            BottomBarScreen.AdminManageApartmentsType,
            BottomBarScreen.Profile
        )
    }
}

@Composable
fun PageTitleCard(title: String, icon: ImageVector? = null) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 30.dp, shape = RentlyCardShape.large) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if(icon != null){
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(Modifier.width(10.dp))
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
//
//@RequiresApi(Build.VERSION_CODES.N)
//@Preview
//@Composable
//fun MainScreenPreview() {
//    MainScreen(onLogout = {}, onFloatingButtonClicked = {})
//}

