package com.pvsb.presentation.main.categories.allScreen.privateContacts

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.TypedMessage

data class PrivateContactState(
    val error: TypedMessage? = null,
    val contactsList: List<Contact> = emptyList()
)
