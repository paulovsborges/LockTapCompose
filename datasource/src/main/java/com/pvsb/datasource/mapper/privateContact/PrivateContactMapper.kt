package com.pvsb.datasource.mapper.privateContact

import com.pvsb.domain.entity.PrivateContact
import locktap.locktapdb.PrivateContactEntity

object PrivateContactMapper {

    fun PrivateContactEntity.toModel(): PrivateContact {
        return this.run {
            PrivateContact(
                id.toString(),
                name = name,
                phoneNumber = phoneNumber,
                imageFilePath = imageFilePath,
                isFavorite = isFavoriteBool(isFavorite.toInt())
            )
        }
    }

    fun PrivateContact.toEntity(): PrivateContactEntity {
        return this.run {
            PrivateContactEntity(
                id = contactId.toLong(),
                name = name,
                phoneNumber = phoneNumber,
                imageFilePath = imageFilePath,
                isFavorite = isFavoriteLong(isFavorite)
            )
        }
    }

    private fun isFavoriteBool(code: Int): Boolean {
        return code != 0
    }

    private fun isFavoriteLong(isFavorite: Boolean): Long {
        return if (isFavorite) 1L else 0L
    }
}