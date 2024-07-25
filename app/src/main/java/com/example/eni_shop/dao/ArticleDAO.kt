package com.example.eni_shop.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.eni_shop.bo.Article

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM Article WHERE id = :id")
    fun findById(id : Long) : Article?

    @Insert
    fun insert(article: Article) : Long

    @Query("SELECT * FROM ARTICLE")
    fun findAll() : List<Article>

    @Delete
    fun delete(article: Article)
}