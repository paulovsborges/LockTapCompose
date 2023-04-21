package com.pvsb.datasource.mapper.photoVault

import com.pvsb.datasource.mapper.isFavorite
import com.pvsb.domain.entity.Photo
import locktap.locktapdb.PhotoVaultEntity

object PhotoMapper {

    fun Photo.toEntity(): PhotoVaultEntity {
        return this.run {
            PhotoVaultEntity(
                id, imageFilePath, isFavorite(isFavorite)
            )
        }
    }

    fun PhotoVaultEntity.toModel(): Photo {
        return this.run {
            Photo(
                id, imageFilePath, isFavorite(isFavorite)
            )
        }
    }
}