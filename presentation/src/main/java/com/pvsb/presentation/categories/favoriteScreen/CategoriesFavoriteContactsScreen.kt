package com.pvsb.presentation.categories.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pvsb.domain.entity.Contact
import com.pvsb.presentation.contact.contactList.ContactsViewModel
import com.pvsb.presentation.utils.components.ComposeContactCell

@Composable
fun CategoriesFavoriteContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel()
) {

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

    Column(modifier = Modifier.padding(top = 20.dp)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = dummyData) {
                ComposeContactCell(contactData = it)
            }
        }
    }
}

@Preview
@Composable
private fun CategoriesFavoriteContactsScreenPreview() {
    CategoriesFavoriteContactsScreen()
}
