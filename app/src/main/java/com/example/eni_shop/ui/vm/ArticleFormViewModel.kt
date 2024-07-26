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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleFormViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories : StateFlow<List<String>>
        get() = _categories

    private val _name = MutableStateFlow("")
    val name : StateFlow<String>
        get() = _name

    private val _description = MutableStateFlow("")
    val description : StateFlow<String>
        get() = _description

    private val _price = MutableStateFlow("")
    val price : StateFlow<String>
        get() = _price

    private val _category = MutableStateFlow("-Choisir une catégorie-")
    val category : StateFlow<String>
        get() = _category

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value = articleRepository.getCategories()
        }
    }

    fun setName(newName : String){
        _name.value = newName
    }

    fun setDescription(newDescription : String){
        _description.value = newDescription
    }

    fun setPrice(newPrice : String){
        _price.value = newPrice
    }

    fun setCategory(categorySelected : String){
        _category.value = categorySelected
    }

    fun addArticle(){
        val newArticle = Article(
            name = _name.value,
            price = _price.value.toDouble(),
            description = _description.value,
            category = _category.value
        )
        viewModelScope.launch {
            articleRepository.addArticle(article = newArticle)
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

                return ArticleFormViewModel(
                    ArticleRepository(
                        EniShopDatabase.getInstance(application.applicationContext).getArticleDAO(),
                        EniShopApiService.ArticleApi.retrofitService
                    )
                ) as T
            }
        }
    }
}