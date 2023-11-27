package com.example.jetcodeapp.ui.page.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jetcodeapp.R
import com.example.jetcodeapp.ui.theme.JetCodeAppTheme

@Composable
fun AboutPage(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    AboutContent(
        modifier = modifier,
        onBackClick = navigateBack
    )
}

@Composable
fun AboutContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.Black,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable { onBackClick() }
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Me",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                modifier = modifier
                    .padding(start = 8.dp)
            )
            AsyncImage(
                model = stringResource(R.string.picture_profile),
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(R.string.name),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(R.string.email),
                style =
                TextStyle(
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutContent() {
    JetCodeAppTheme {
        AboutContent {}
    }
}