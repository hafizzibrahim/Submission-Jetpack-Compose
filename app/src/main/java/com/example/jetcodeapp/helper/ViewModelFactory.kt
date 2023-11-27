package com.example.jetcodeapp.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetcodeapp.data.CodeRepository
import com.example.jetcodeapp.ui.page.detail.DetailViewModel
import com.example.jetcodeapp.ui.page.favorite.FavoriteViewModel
import com.example.jetcodeapp.ui.page.home.HomeViewModel

class ViewModelFactory(private val repository: CodeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}