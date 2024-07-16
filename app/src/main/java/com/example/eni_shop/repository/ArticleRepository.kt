package com.example.eni_shop.repository

import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.DAOFactory
import com.example.eni_shop.dao.DAOType

class ArticleRepository {

    private val articleDAO = DAOFactory.createArticleDao(DAOType.MEMORY)

    fun getArticle(id : Long) :Article?{
        return articleDAO.findById(id)
    }

    fun addArticle(article: Article) : Long {
        return articleDAO.insert(article)
    }

}