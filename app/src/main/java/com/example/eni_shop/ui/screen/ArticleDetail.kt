package com.example.eni_shop.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.eni_shop.ui.common.TopBar

@Composable
fun ArticleDetailScreen() {
    Scaffold(
        topBar = { TopBar() }
    ) {
        ArticleDetail(modifier = Modifier.padding(it))
    }
}


@Composable
fun ArticleDetail(modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxHeight()) {
        Text("Titre de mon article")
        AsyncImage(
            model = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            contentDescription = "Titre de l'article",
            modifier = Modifier.size(200.dp)
        )
        Text("Description de mon article")
        Row {
            Text(text = "Prix : 22.30 €")
            Text(text = "Date de sortie : 22/07/2024")
        }
        Row {
            Checkbox(checked = true, onCheckedChange = {})
            Text(text = "Favoris ?")
        }
    }

}

@Composable
@Preview
fun Preview() {
    ArticleDetail()
}