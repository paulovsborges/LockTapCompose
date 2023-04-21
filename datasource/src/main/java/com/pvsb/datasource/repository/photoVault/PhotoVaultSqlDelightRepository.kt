package com.pvsb.datasource.repository.photoVault

import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.locktapcompose.LockTapDataBase

class PhotoVaultSqlDelightRepository(
    db: LockTapDataBase
): PhotoVaultRepository {

    private val queries = db.photoVaultQueries

    override suspend fun getPhotoById(photoId: Long): Photo? {
        return null
//        return queries.getById(photoId).executeAsOneOrNull()
    }

    override suspend fun getAllPhotos(): List<Photo> {
        TODO("Not yet implemented")
    }

    override suspend fun addOrReplacePhoto(photo: Photo) {
        TODO("Not yet implemented")
    }

    override suspend fun togglePhotoFavorite(photoId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePhoto(photoId: Long) {
        TODO("Not yet implemented")
    }
}