package com.example.jetcodeapp.ui.page.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.jetcodeapp.R
import com.example.jetcodeapp.di.Injection
import com.example.jetcodeapp.helper.ViewModelFactory
import com.example.jetcodeapp.model.CodeItem
import com.example.jetcodeapp.ui.common.UiState

@Composable
fun DetailPage(
    codeId: String,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
) {
    val isFavorite = remember { mutableStateOf(false) }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCodeById(codeId)
            }

            is UiState.Success<*> -> {
                val data = uiState.data as CodeItem

                viewModel.checkFavorite(codeId) { isCodeFavorite ->
                    isFavorite.value = isCodeFavorite
                }

                DetailContent(
                    bannerUrl = data.item.bannerUrl,
                    photoAuthorUrl = data.item.photoAuthorUrl,
                    codeName = data.item.codeName,
                    desc = data.item.desc,
                    author = data.item.author,
                    onBackClick = navigateBack,
                    isFavorite = isFavorite.value,
                    onToggleFavorite = {
                        if (isFavorite.value) {
                            viewModel.removeFromFavorite(codeId)
                            isFavorite.value = false
                        } else {
                            viewModel.addToFavorites(codeId)
                            isFavorite.value = true
                        }
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    bannerUrl: String,
    photoAuthorUrl: String,
    codeName: String,
    desc: String,
    author: String,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onBackClick() }
                    )
                    IconButton(
                        onClick = { onToggleFavorite() },
                        modifier = modifier.padding(top = 8.dp)
                    ) {
                        val icon =
                            if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                        Icon(
                            imageVector = icon,
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                            contentDescription = stringResource(R.string.add_favorite),
                        )
                    }
                }
            }
            AsyncImage(
                model = bannerUrl,
                contentDescription = "banner",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(350.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
            ) {
                Text(
                    text = codeName,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Author",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Left,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        AsyncImage(
                            model = photoAuthorUrl,
                            contentDescription = "author",
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(8.dp)
                                .size(128.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = modifier.width(16.dp))
                        Text(
                            text = author,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 5.dp)
                                .align(Alignment.Start)
                        )
                    }
                }

                Text(
                    text = "Description",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.Start)
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(top = 5.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .padding(top = 16.dp)
                )
            }
        }
    }
}
