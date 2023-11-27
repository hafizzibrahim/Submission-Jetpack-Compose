package com.example.jetcodeapp.ui.page.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcodeapp.data.CodeRepository
import com.example.jetcodeapp.model.CodeItem
import com.example.jetcodeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CodeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CodeItem>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<CodeItem>> get() = _uiState

    fun getCodeById(codeId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCodeItemById(codeId))
        }
    }

    fun addToFavorites(codeId: String) {
        viewModelScope.launch {
            repository.addToFavorites(codeId)
        }
    }

    fun removeFromFavorite(codeId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(codeId)
        }
    }

    fun checkFavorite(codeId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(codeId)
            onResult(isFavorite)
        }
    }
}