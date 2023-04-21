package com.pvsb.domain.repository

import com.pvsb.domain.entity.Photo

interface PhotoVaultRepository {

    suspend fun getPhotoById(photoId: Long): Photo?
    suspend fun getAllPhotos(): List<Photo>
    suspend fun addOrReplacePhoto(photo: Photo)
    suspend fun deletePhoto(photoId: Long)
}