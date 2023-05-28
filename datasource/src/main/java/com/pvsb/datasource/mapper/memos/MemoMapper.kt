package com.pvsb.datasource.mapper.memos

import com.pvsb.datasource.mapper.isFavorite
import com.pvsb.domain.entity.Memo
import locktap.locktapdb.MemoEntity
import java.text.SimpleDateFormat
import java.util.Date

object MemoMapper {

    fun Memo.toEntity(): MemoEntity {
        return this.run {
            MemoEntity(
                id = id.toLong(),
                title = title,
                description = description,
                createdAt = formatDateToString(createdAt),
                isFavorite = isFavorite(isFavorite)
            )
        }
    }

    fun MemoEntity.toModel(): Memo {
        return this.run {
            Memo(
                id = id.toString(),
                title = title,
                description = description,
                createdAt = getDateFromString(createdAt),
                isFavorite = isFavorite(isFavorite)
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