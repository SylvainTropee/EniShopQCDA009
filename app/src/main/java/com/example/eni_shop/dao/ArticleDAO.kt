package com.example.eni_shop.dao

import com.example.eni_shop.bo.Article

interface ArticleDAO {

    fun findById(id : Long) : Article?

    fun insert(article: Article) : Long
}