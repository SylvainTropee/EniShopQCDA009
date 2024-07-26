package com.example.eni_shop.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.eni_shop.services.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun TitleApp() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Shop",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "ENI-SHOP",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 40.sp
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navHostController: NavHostController,
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = { TitleApp() },
//        navigationIcon = navigationIcon
        navigationIcon = {
            if (navHostController.previousBackStackEntry != null) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    }
                )
            }
        },
        actions = { SettingsMenu() }
    )
}

@Composable
fun SettingsMenu() {
    val context = LocalContext.current
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    var checked by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        DataStoreManager.isDarkModeActivated(context).collect {
            checked = it
        }
    }


    val coroutineScope = rememberCoroutineScope()

    Icon(
        imageVector = Icons.Default.Menu, contentDescription = "Settings menu",
        modifier = Modifier.clickable {
            expanded = !expanded
        }
    )
    DropdownMenu(
        expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { Text("Dark Theme") },
            trailingIcon = {
                Switch(checked = checked, onCheckedChange = {
                    coroutineScope.launch(Dispatchers.IO) {
                        DataStoreManager.setDarkMode(context, it)
                    }
                })
            },
            onClick = { /*TODO*/ }
        )

    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Preview
@Composable
fun Preview() {
    LoadingScreen()
}