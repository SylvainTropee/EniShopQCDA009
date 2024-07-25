package com.example.eni_shop.dao

import com.example.eni_shop.dao.memory.ArticleDAOMemoryImpl

abstract class DAOFactory {

    companion object {

        fun createArticleDao(type: DAOType): ArticleDAO {
            val articleDAO: ArticleDAO = ArticleDAOMemoryImpl()
            return articleDAO
        }
    }


}