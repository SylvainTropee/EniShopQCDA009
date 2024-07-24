package com.example.eni_shop.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

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
    navigationIcon : @Composable () -> Unit = {}
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
        }
    )
}

@Preview
@Composable
fun Preview() {
    TitleApp()
}