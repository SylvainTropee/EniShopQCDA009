package com.example.eni_shop.services

import com.example.eni_shop.bo.Article
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EniShopApiService {

    companion object {

        const val BASE_URL = "https://fakestoreapi.com/"

        //convertisseur permet de transformer le JSon en objet
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        //librairie qui permet d'envoyer des requêtes http
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    @GET("products")
    suspend fun getArticles() : List<Article>

    @GET("products/{id}")
    suspend fun getArticleById(@Path("id") id : Long) : Article

    @POST("products")
    suspend fun addArticle(@Body article: Article) : Long

    @GET("products/categories")
    suspend fun getCategories() : List<String>


    object ArticleApi {
        val retrofitService: EniShopApiService by lazy { retrofit.create(EniShopApiService::class.java) }
    }


}