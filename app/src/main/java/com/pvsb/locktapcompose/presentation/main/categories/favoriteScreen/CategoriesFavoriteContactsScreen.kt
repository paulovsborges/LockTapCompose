package com.pvsb.locktapcompose.presentation.main.categories.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.presentation.utils.components.ComposeContactCell

@Composable
fun CategoriesFavoriteContactsScreen() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(2) {
                ComposeContactCell()
            }
        }
    }
}

@Preview
@Composable
private fun CategoriesFavoriteContactsScreenPreview() {
    CategoriesFavoriteContactsScreen()
}