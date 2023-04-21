package com.pvsb.presentation.categories.allScreen.photoVault

import com.pvsb.domain.entity.Photo

data class PhotoVaultScreenState(
    val photos: List<Photo> = emptyList()
)
