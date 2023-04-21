package com.pvsb.datasource.repository.photoVault

import com.pvsb.datasource.mapper.photoVault.PhotoMapper.toEntity
import com.pvsb.datasource.mapper.photoVault.PhotoMapper.toModel
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.locktapcompose.LockTapDataBase

class PhotoVaultSqlDelightRepository(
    db: LockTapDataBase
) : PhotoVaultRepository {

    private val queries = db.photoVaultQueries

    override suspend fun getPhotoById(photoId: Long): Photo? {
        return queries.getById(photoId).executeAsOneOrNull()?.toModel()
    }

    override suspend fun getAllPhotos(): List<Photo> {
        return queries.getAll().executeAsList().map { it.toModel() }
    }

    override suspend fun addOrReplacePhoto(photo: Photo) {
        queries.isertOrReplace(photo.toEntity())
    }

    override suspend fun deletePhoto(photoId: Long) {
        queries.delete(photoId)
    }
}