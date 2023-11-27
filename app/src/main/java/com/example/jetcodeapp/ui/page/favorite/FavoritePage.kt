package com.example.jetcodeapp.ui.page.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetcodeapp.R
import com.example.jetcodeapp.di.Injection
import com.example.jetcodeapp.helper.ViewModelFactory
import com.example.jetcodeapp.model.CodeItem
import com.example.jetcodeapp.ui.common.UiState
import com.example.jetcodeapp.ui.components.CodeListItem
import com.example.jetcodeapp.ui.theme.JetCodeAppTheme

@Composable
fun FavoritePage(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    val favoriteCodes by viewModel.favoriteCodes.collectAsState(emptyList())

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFavoriteCodes()
            }

            is UiState.Success -> {
                FavoriteContent(
                    favoriteCodes = favoriteCodes,
                    onBackClick = navigateBack,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    favoriteCodes: List<CodeItem>,
    onBackClick: () -> Unit,
    navigateToDetail: (String) -> Unit
) {

    Column(modifier = Modifier.padding(8.dp)) {

        Box {

            // back icon

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.clickable { onBackClick() }
                )

            }

        }

        Column {
            Text(
                text = "Favorite",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (favoriteCodes.isEmpty()) {

                Text(
                    text = stringResource(R.string.empty_favorite),
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .testTag("EmptyFavoriteText"),
                    color = Color.LightGray,
                    textAlign = TextAlign.Justify
                )

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    items(favoriteCodes) { codeItem ->

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

                }

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteContent() {
    JetCodeAppTheme {
        FavoriteContent(
            favoriteCodes = listOf(),
            onBackClick = {},
            navigateToDetail = {}
        )
    }
}