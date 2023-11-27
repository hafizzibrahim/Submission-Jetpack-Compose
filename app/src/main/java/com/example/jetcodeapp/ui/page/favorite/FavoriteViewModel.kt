package com.example.jetcodeapp.ui.page.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcodeapp.data.CodeRepository
import com.example.jetcodeapp.model.CodeItem
import com.example.jetcodeapp.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: CodeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<CodeItem>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<CodeItem>>> get() = _uiState

    val favoriteCodes: Flow<List<CodeItem>> = repository.getFavoriteCodes()

    fun getAllFavoriteCodes() {
        viewModelScope.launch {
            repository.getFavoriteCodes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favoriteAnimeItems ->
                    _uiState.value = UiState.Success(favoriteAnimeItems)
                }
        }
    }
}