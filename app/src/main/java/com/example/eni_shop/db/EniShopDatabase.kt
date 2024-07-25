package com.example.eni_shop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDAO
import com.example.eni_shop.utils.Converters

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class EniShopDatabase : RoomDatabase() {

    //liste des daos à utiliser
    abstract fun getArticleDAO() : ArticleDAO

    companion object {

        private var INSTANCE: EniShopDatabase? = null

        fun getInstance(context: Context): EniShopDatabase {

            var instance = INSTANCE

            if (instance == null) {

                instance = Room.databaseBuilder(
                    context,
                    EniShopDatabase::class.java,
                    "EniShop.db"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
            }
            return instance
        }
    }
}