package com.example.eni_shop

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.eni_shop.bo.Article
import com.example.eni_shop.ui.screen.ARTICLE_DESCRIPTION_TAG
import com.example.eni_shop.ui.screen.ARTICLE_NAME_TAG
import com.example.eni_shop.ui.screen.ArticleDetail
import org.junit.Rule
import org.junit.Test
import java.util.Date

class ArticleDetailTest {

    //récupérer la règle
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displayArticleTest() {

        val article = Article(
            id = 3,
            name = "Mens Cotton Jacket",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99,
            urlImage = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            category = "men's clothing",
            date = Date()
        )

        //passer le composant à tester
        composeTestRule.setContent {
            ArticleDetail(
                article = article
            )
        }

        composeTestRule
            .onNodeWithTag(ARTICLE_NAME_TAG)
            .assert(hasText(article.name))
        composeTestRule
            .onNodeWithTag(ARTICLE_DESCRIPTION_TAG)
            .assert(hasText(article.description))

    }


}