package com.example.eni_shop.repository

import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDAO
import com.example.eni_shop.dao.DAOType
import com.example.eni_shop.services.EniShopApiService

class ArticleRepository(
    private val articleDaoRoomImpl: ArticleDAO,
    private val eniShopApiService: EniShopApiService
) {

    //private val eniShopApiService = DAOFactory.createArticleDao(DAOType.NETWORK)

    suspend fun getArticle(id: Long, type: DAOType = DAOType.NETWORK): Article? {
        when (type) {
            DAOType.NETWORK -> return eniShopApiService.getArticleById(id)
            else -> return articleDaoRoomImpl.findById(id)
        }
    }

    suspend fun addArticle(article: Article, type: DAOType = DAOType.NETWORK): Any {
        when (type) {
            DAOType.NETWORK -> return eniShopApiService.addArticle(article)
            else -> return articleDaoRoomImpl.insert(article)
        }
    }

    suspend fun getAllArticles(): List<Article> {
        return eniShopApiService.getArticles()
    }

    fun deleteArticleFav(article: Article) {
        articleDaoRoomImpl.delete(article)
    }

    suspend fun getCategories(): List<String> {
        return eniShopApiService.getCategories()
    }


}