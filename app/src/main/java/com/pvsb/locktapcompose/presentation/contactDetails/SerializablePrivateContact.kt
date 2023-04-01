package com.pvsb.locktapcompose.presentation.contactDetails

@kotlinx.serialization.Serializable
data class SerializablePrivateContact(
    val contactId: String,
    val name: String,
    val phoneNumber: String,
    val imageFilePath: String?
) {
    companion object{
        const val PRIVATE_CONTACT_DATA_KEY = "PRIVATE_CONTACT_DATA_KEY"
    }
}
