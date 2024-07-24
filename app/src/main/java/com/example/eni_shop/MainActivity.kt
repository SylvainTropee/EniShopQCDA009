package com.example.eni_shop

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eni_shop.destination.EniShopArticleDetailDestination
import com.example.eni_shop.destination.EniShopArticleFormDestination
import com.example.eni_shop.destination.EniShopArticleListDestination
import com.example.eni_shop.repository.ArticleRepository
import com.example.eni_shop.services.DataStoreManager
import com.example.eni_shop.ui.screen.ArticleDetailScreen
import com.example.eni_shop.ui.screen.ArticleFormScreen
import com.example.eni_shop.ui.screen.ArticleListScreen
import com.example.eni_shop.ui.theme.ENISHOPTheme
import kotlinx.coroutines.coroutineScope

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val context = this

        setContent {

            var isDarkModeActivated by rememberSaveable {
                mutableStateOf(false)
            }

            LaunchedEffect(Unit) {
                DataStoreManager.isDarkModeActivated(this@MainActivity).collect {
                    isDarkModeActivated = it
                }
            }


            ENISHOPTheme(darkTheme = isDarkModeActivated) {
                EniShopApp(isDarkModeActivated = isDarkModeActivated)
            }
        }
    }
}

@Composable
fun EniShopApp(isDarkModeActivated : Boolean) {
    val navHostController = rememberNavController()
    EniShopNavHost(
        navHostController = navHostController,
        isDarkModeActivated = isDarkModeActivated
    )
}


@Composable
fun EniShopNavHost(
    navHostController: NavHostController,
    isDarkModeActivated : Boolean
) {

    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = EniShopArticleListDestination.route
    ) {

        this.composable(EniShopArticleListDestination.route) {
            ArticleListScreen(
                onClickToAddArticle = { navHostController.navigate(EniShopArticleFormDestination.route) },
                onClickToDetailArticle = {
                    navHostController.navigate("${EniShopArticleDetailDestination.route}/$it") {
                        launchSingleTop = true
                    }
                },
                navHostController = navHostController,
                isDarkModeActivated = isDarkModeActivated
            )
        }
        this.composable(
            EniShopArticleDetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(EniShopArticleDetailDestination.articleDetailArg) {
                    type = NavType.LongType
                }
            )
        ) {
            val articleId = it.arguments?.getLong(EniShopArticleDetailDestination.articleDetailArg)
            if (articleId != null) {
                ArticleDetailScreen(
                    articleId = articleId,
                    navHostController = navHostController,
                    isDarkModeActivated = isDarkModeActivated
                )
            } else {
                Toast.makeText(context, "Article non disponible !", Toast.LENGTH_SHORT).show()
            }

        }
        this.composable(route = EniShopArticleFormDestination.route) {
            ArticleFormScreen(
                navHostController = navHostController,
                isDarkModeActivated = isDarkModeActivated,
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

    }


}

















