package com.example.eni_shop.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eni_shop.bo.Article
import com.example.eni_shop.ui.common.TopBar
import com.example.eni_shop.ui.vm.ArticleDetailViewModel
import com.example.eni_shop.utils.convertDateToStringFR

const val ARTICLE_NAME_TAG = "ARTICLE_NAME"
const val ARTICLE_DESCRIPTION_TAG = "ARTICLE_DESCRIPTION"

@Composable
fun ArticleDetailScreen(
    articleId: Long,
    navHostController: NavHostController,
    articleDetailViewModel: ArticleDetailViewModel = viewModel(factory = ArticleDetailViewModel.Factory)
) {

    val article by articleDetailViewModel.article.collectAsState()

    LaunchedEffect(Unit) {
        articleDetailViewModel.loadArticleById(articleId)
    }


    Scaffold(
        topBar = { TopBar(navHostController = navHostController) }
    ) {
        ArticleDetail(
            article = article,
            modifier = Modifier.padding(it)
        )
    }
}


@Composable
fun ArticleDetail(
    article: Article,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = article.name,
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Justify,
            lineHeight = 1.em,
            modifier = Modifier.clickable {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://www.google.com/search?q=eni+shop+${
                            article.name.replace(
                                " ",
                                "+"
                            )
                        }"
                    )
                ).also {
                    context.startActivity(it)
                }
            }.testTag(ARTICLE_NAME_TAG)
        )
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = article.name,
                modifier = Modifier.size(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = article.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier.testTag(ARTICLE_DESCRIPTION_TAG)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Prix : ${String.format("%.2f", article.price)} €")
            Text(text = "Date de sortie : ${article.date.convertDateToStringFR()}")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = true, onCheckedChange = {})
            Text(text = "Favoris ?")
        }
    }

}

@Composable
@Preview
fun Preview() {
    //ArticleDetail()
}