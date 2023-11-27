package com.example.jetcodeapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CodeListItem(
    codeName: String,
    desc: String,
    bannerUrl: String,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = bannerUrl,
                contentDescription = "banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = codeName,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = desc,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium

                )

            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun CodeListItemPreview() {
    MaterialTheme {
        CodeListItem(codeName = "Hafiz Ibrahim", desc = "Lorem ipsum dolor sit amet", bannerUrl = "", modifier = Modifier.padding(8.dp))
    }
}