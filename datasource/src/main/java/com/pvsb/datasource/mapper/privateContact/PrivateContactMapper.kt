package com.pvsb.datasource.mapper.privateContact

import com.pvsb.datasource.mapper.isFavorite
import com.pvsb.domain.entity.Contact
import locktap.locktapdb.PrivateContactEntity

object PrivateContactMapper {

    fun PrivateContactEntity.toModel(): Contact {
        return this.run {
            Contact(
                id.toString(),
                name = name,
                phoneNumber = phoneNumber,
                imageFilePath = imageFilePath,
                isFavorite = isFavorite(isFavorite)
            )
        }
    }

    fun Contact.toEntity(): PrivateContactEntity {
        return this.run {
            PrivateContactEntity(
                id = contactId.toLong(),
                name = name,
                phoneNumber = phoneNumber,
                imageFilePath = imageFilePath,
                isFavorite = isFavorite(isFavorite)
            )
        }
    }
}
