package com.example.eni_shop.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.eni_shop.bo.Article
import com.example.eni_shop.db.EniShopDatabase
import com.example.eni_shop.repository.ArticleRepository
import com.example.eni_shop.services.EniShopApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>>
        get() = _articles

    val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val a = async { _articles.value = articleRepository.getAllArticles() }
            val c = async { _categories.value = articleRepository.getCategories() }
            awaitAll(a, c)
            _isLoading.value = false
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

                return ArticleListViewModel(
                    ArticleRepository(
                        EniShopDatabase.getInstance(application.applicationContext).getArticleDAO(),
                        EniShopApiService.ArticleApi.retrofitService
                    )
                ) as T
            }
        }
    }

}