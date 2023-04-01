package com.pvsb.locktapcompose.presentation.main.categories.favoriteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun FavoriteScreenContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(Color.Blue), contentAlignment = Alignment.Center) {
        Text(text = "favorites content")
    }
}

@Preview
@Composable
fun FavoriteScreenContentPreview() {
    FavoriteScreenContent()
}
