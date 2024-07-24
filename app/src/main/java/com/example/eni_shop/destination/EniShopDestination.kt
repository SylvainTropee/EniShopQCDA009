package com.example.eni_shop.destination

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface EniShopDestination{
    val route : String
}


object EniShopArticleListDestination : EniShopDestination{
    override val route = "articleList"
}

object EniShopArticleDetailDestination : EniShopDestination{
    override val route = "articleDetail"
    val articleDetailArg = "articleId"
    val arguments = listOf(
        navArgument(articleDetailArg) { type = NavType.LongType }
    )
    val routeWithArgs = "$route/{$articleDetailArg}"
}

object EniShopArticleFormDestination : EniShopDestination{
    override val route = "articleForm"
}