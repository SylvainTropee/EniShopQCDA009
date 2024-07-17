package com.example.eni_shop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eni_shop.bo.Article
import com.example.eni_shop.repository.ArticleRepository
import com.example.eni_shop.ui.screen.ArticleDetail
import com.example.eni_shop.ui.screen.ArticleDetailScreen
import com.example.eni_shop.ui.theme.ENISHOPTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val articleRepository = ArticleRepository()

        val id = articleRepository.addArticle(Article(name = "Article 4"))
        Log.i(TAG, "id: $id")
        val article = articleRepository.getArticle(id)
        Log.i(TAG, article.toString())

        setContent {
            ENISHOPTheme {
                ArticleDetailScreen()
            }
        }
    }
}

