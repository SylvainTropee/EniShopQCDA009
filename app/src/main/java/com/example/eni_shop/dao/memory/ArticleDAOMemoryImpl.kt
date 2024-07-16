package com.example.eni_shop.dao.memory

import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDAO

class ArticleDAOMemoryImpl : ArticleDAO {

    companion object {

        private val articlesInMemory: MutableList<Article> = mutableListOf(
            Article(name = "Article 1", id = 1),
            Article(name = "Article 2", id = 2),
            Article(name = "Article 3", id = 3),
        )
    }

    override fun findById(id: Long): Article? {
        return articlesInMemory.find { article ->
            article.id == id
        }
    }

    override fun insert(article: Article): Long {
        articlesInMemory.add(article)
        article.id = articlesInMemory.size.toLong()
        return article.id
    }

}