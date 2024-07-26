package com.example.eni_shop.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.DAOType
import com.example.eni_shop.db.EniShopDatabase
import com.example.eni_shop.repository.ArticleRepository
import com.example.eni_shop.services.EniShopApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _article = MutableStateFlow<Article>(Article())
    val article: StateFlow<Article>
        get() = _article

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean>
        get() = _isFavorite

    fun loadArticleById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentArticle = articleRepository.getArticle(id)
            if (currentArticle != null) {
                _article.value = currentArticle

                val articleFav = articleRepository.getArticle(id, DAOType.ROOM)
                if (articleFav != null) {
                    _isFavorite.value = true
                }
            }
        }
    }

    fun saveArticleFav() {
        viewModelScope.launch(Dispatchers.IO) {
            articleRepository.addArticle(_article.value, DAOType.ROOM)
            _isFavorite.value = true
        }
    }

    fun deleteArticleFav() {
        viewModelScope.launch(Dispatchers.IO) {
            articleRepository.deleteArticleFav(_article.value)
            _isFavorite.value = false
        }
    }

    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return ArticleDetailViewModel(
                    ArticleRepository(
                        EniShopDatabase.getInstance(application.applicationContext).getArticleDAO(),
                        EniShopApiService.ArticleApi.retrofitService
                    )
                ) as T
            }
        }
    }
}