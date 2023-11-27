package com.example.jetcodeapp.ui.page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcodeapp.data.CodeRepository
import com.example.jetcodeapp.model.Code
import com.example.jetcodeapp.model.CodeItem
import com.example.jetcodeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CodeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Map<Char, List<CodeItem>>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Map<Char, List<CodeItem>>>> get() = _uiState

    private val _searchResult = MutableStateFlow<List<CodeItem>>(emptyList())
    val searchResult: StateFlow<List<CodeItem>> get() = _searchResult

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    fun getAllCodes() {
        viewModelScope.launch {
            repository.getSortedAndGroupedCode()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { groupedCodeItems ->
                    _uiState.value = UiState.Success(groupedCodeItems)
                }
        }
    }

    fun searchCodes() {
        val currentQuery = _query.value
        viewModelScope.launch {
            repository.searchCodes(currentQuery)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { searchResult ->
                    _searchResult.value = searchResult
                }
        }
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }
}