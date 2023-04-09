package com.pvsb.presentation.contact.contactList

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.TypedMessage

data class PrivateContactState(
    val error: TypedMessage? = null,
    val contactsList: List<Contact> = emptyList(),
    val contactDetails: ContactDetailsState = ContactDetailsState()
)

data class ContactDetailsState(
    val details: Contact = Contact(
        "", "", "", null, false
    ),
    val isSaveButtonEnabled: Boolean = false
) {

    fun toggleButtonEnabled(newData: Contact): ContactDetailsState {
        return this.copy(isSaveButtonEnabled = this.details != newData)
    }
}