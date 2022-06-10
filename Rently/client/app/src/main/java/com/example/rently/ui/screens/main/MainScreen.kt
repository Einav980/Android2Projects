package com.example.rently.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rently.R
import com.example.rently.navigation.TopBarScreen
import com.example.rently.navigation.TopNavGraph
import com.example.rently.ui.screens.main.MainScreenViewModel
import com.example.rently.ui.theme.RentlyDrawerItemBackground
import com.example.rently.ui.theme.RentlyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(onFloatingButtonClicked: () -> Unit,
               onLogout: () -> Unit,
               viewModel: MainScreenViewModel = hiltViewModel()) {

    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()


    RentlyTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
            drawerContent = { Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController) },
            floatingActionButton = { FloatingButton(onFloatingButtonClicked) },
            drawerElevation = 10.dp,
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                TopNavGraph(navController = navController, onLogout = onLogout)
            }
        }
    }
}

@Composable
fun FloatingButton(onFloatingButtonClicked: () -> Unit) {
    FloatingActionButton(
        onClick = {onFloatingButtonClicked()}
    ) {
        Icon(Icons.Filled.Add,"")
    }
}


@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {

    TopAppBar(
        title = { Text(text = "Rently", fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        }
    )
    MaterialTheme {
        TopAppBar(
            title = {
                Text(
                    text = "Rently",
                    style = MaterialTheme.typography.h4,
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, "")
                }
            },
        )
    }
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    val items = listOf(
        TopBarScreen.Apartments,
        TopBarScreen.Settings,
        TopBarScreen.Profile,
        TopBarScreen.ManageApartmentTypes,
        TopBarScreen.Logout
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            UserHead(firstName = "Tom", lastName = "Segal", Modifier.size(100.dp))
        }

        Divider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(15.dp))

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {
                navController.navigate(items.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }
    }
}

@Composable
fun DrawerItem(
    item: TopBarScreen,
    selected: Boolean,
    onItemClick: (TopBarScreen) -> Unit
) {
    MaterialTheme {
        val background = if (selected) RentlyDrawerItemBackground else Color.Transparent
        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 3.dp, bottom = 3.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(background)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
                    .height(45.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(24.dp)
                        .weight(1f),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = item.title,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    modifier = Modifier
                        .weight(5f),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun UserHead(
    firstName: String,
    lastName: String,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    textStyle: TextStyle = MaterialTheme.typography.h3,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primaryVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${firstName[0].uppercaseChar()}${lastName[0].uppercaseChar()}",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            color = Color.White,
            letterSpacing = 5.sp
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(onLogout = {}, onFloatingButtonClicked = {})
}
