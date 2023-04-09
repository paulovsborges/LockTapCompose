package com.pvsb.presentation.contact.contactDetails

import com.pvsb.domain.entity.Contact

@kotlinx.serialization.Serializable
data class SerializableContact(
    val contactId: String,
    val name: String,
    val phoneNumber: String,
    val imageFilePath: String?,
    val isFavorite: Boolean
) {
    companion object {
        const val PRIVATE_CONTACT_DATA_KEY = "PRIVATE_CONTACT_DATA_KEY"
    }

    fun toModel(): Contact {
        return this.run {
            Contact(
                contactId, name, phoneNumber, imageFilePath, isFavorite
            )
        }
    }
}
