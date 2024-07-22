package com.example.eni_shop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eni_shop.repository.ArticleRepository
import com.example.eni_shop.ui.screen.ArticleListScreen
import com.example.eni_shop.ui.theme.ENISHOPTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val articleRepository = ArticleRepository()

        val article = articleRepository.getArticle(2)
        Log.i(TAG, article.toString())

        setContent {
            ENISHOPTheme {
                ArticleListScreen()
            }
        }
    }
}

