package com.example.eni_shop.repository

import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDAO
import com.example.eni_shop.dao.DAOFactory
import com.example.eni_shop.dao.DAOType

class ArticleRepository(private val articleDaoRoomImpl : ArticleDAO) {

    private val articleDAOMemoryImpl = DAOFactory.createArticleDao(DAOType.MEMORY)

    fun getArticle(id : Long, type : DAOType = DAOType.MEMORY) :Article?{
        when(type){
            DAOType.MEMORY ->  return articleDAOMemoryImpl.findById(id)
            else ->return articleDaoRoomImpl.findById(id)
        }
    }

    fun addArticle(article: Article, type : DAOType = DAOType.MEMORY) : Long {
        when (type){
            DAOType.MEMORY -> return articleDAOMemoryImpl.insert(article)
            else ->  return articleDaoRoomImpl.insert(article)
        }
    }

    fun getAllArticles() : List<Article> {
        return articleDAOMemoryImpl.findAll()
    }

    fun deleteArticleFav(article: Article){
        articleDaoRoomImpl.delete(article)
    }




}