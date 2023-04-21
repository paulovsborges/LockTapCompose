package com.pvsb.domain.repository

import com.pvsb.domain.entity.Photo

interface PhotoVaultRepository {
    suspend fun getAllPhotos(): List<Photo>
    suspend fun addPhoto(photo: Photo)
    suspend fun togglePhotoFavorite(photoId: Long)
    suspend fun deletePhoto(photoId: Long)
}