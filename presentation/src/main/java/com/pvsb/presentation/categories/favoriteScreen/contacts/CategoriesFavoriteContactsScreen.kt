package com.pvsb.presentation.categories.favoriteScreen.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.domain.entity.Contact
import com.pvsb.presentation.categories.favoriteScreen.ComposeEmptyQueryResults
import com.pvsb.presentation.utils.components.ComposeContactCell

@Composable
fun CategoriesFavoriteContactsScreen(
    modifier: Modifier = Modifier,
    contacts: List<Contact> = emptyList()
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        if (contacts.isEmpty()) {
            ComposeEmptyQueryResults(modifier.fillMaxSize())
        } else {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize()
            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = contacts) {
                        ComposeContactCell(contactData = it)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CategoriesFavoriteContactsScreenPreview() {

    val dummyData = listOf(
        Contact(
            "12345",
            "John Doe",
            "347-671-1254",
            null,
            false
        ),
        Contact(
            "12345",
            "Brian Doe",
            "827-627-1754",
            "https://images.unsplash.com/photo-1551887373-3c5bd224f6e2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Y3JhenklMjBkb2d8ZW58MHx8MHx8&w=1000&q=80",
            false
        ),
    )

    CategoriesFavoriteContactsScreen(
        modifier = Modifier.fillMaxSize(),
        contacts = dummyData
    )
}
