package com.example.jetcodeapp.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetcodeapp.di.Injection
import com.example.jetcodeapp.helper.ViewModelFactory
import com.example.jetcodeapp.model.CodeItem
import com.example.jetcodeapp.ui.common.UiState
import com.example.jetcodeapp.ui.components.CodeListItem
import com.example.jetcodeapp.ui.components.SearchBar

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    val searchResult by viewModel.searchResult.collectAsState(initial = emptyList())
    val query by viewModel.query.collectAsState(initial = "")

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCodes()
            }

            is UiState.Success -> {
                Column {
                    SearchBar(
                        query = query,
                        onQueryChange = { newQuery ->
                            viewModel.setQuery(newQuery)
                            viewModel.searchCodes()
                        },
                    )
                    HomeContent(
                        groupedCodes = if (query.isEmpty()) uiState.data else emptyMap(),
                        searchResult = searchResult,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    groupedCodes: Map<Char, List<CodeItem>>,
    searchResult: List<CodeItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("CodeList")
    ) {
        if (searchResult.isNotEmpty()) {
            items(searchResult) { codeItem ->

                val code = codeItem.item

                CodeListItem(
                    codeName = code.codeName,
                    desc = code.desc,
                    bannerUrl = code.bannerUrl,
                    modifier = Modifier.clickable {
                        navigateToDetail(code.id)
                    }
                )
            }
        } else {
            groupedCodes.entries.forEach { (_, codeItems) ->
                items(codeItems) { data ->
                    CodeListItem(
                        codeName = data.item.codeName,
                        desc = data.item.desc,
                        bannerUrl = data.item.bannerUrl,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.item.id)
                        }
                    )
                }
            }
        }
    }
}