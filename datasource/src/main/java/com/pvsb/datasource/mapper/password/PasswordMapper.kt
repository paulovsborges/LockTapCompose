package com.pvsb.datasource.mapper.password

import com.pvsb.datasource.mapper.isFavorite
import com.pvsb.domain.entity.Password
import locktap.locktapdb.PasswordsEntity
import java.text.SimpleDateFormat
import java.util.Date

object PasswordMapper {

    fun PasswordsEntity.toModel(): Password {
        return this.run {
            Password(
                id = id.toString(),
                title = title,
                password = password,
                createdAt = getDateFromString(createdAt),
                isFavorite = isFavorite(isFavorite),
                additionalInfo = additionalInfo
            )
        }
    }

    fun Password.toEntity(): PasswordsEntity {
        return this.run {
            PasswordsEntity(
                id = id.toLong(),
                title = title,
                password = password,
                createdAt = formatDateToString(createdAt),
                isFavorite = isFavorite(isFavorite),
                additionalInfo = additionalInfo
            )
        }
    }

    private fun getDateFromString(dateString: String): Date? {
        return try {
            SimpleDateFormat.getInstance().parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun formatDateToString(date: Date?): String {
        return try {
            date?.let {
                SimpleDateFormat.getInstance().format(date)
            } ?: throw NullPointerException("${super.toString()} date is null")
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
