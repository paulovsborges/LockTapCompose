package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import com.pvsb.domain.entity.Photo
import com.pvsb.domain.entity.TypedMessage

data class PhotoDetailsState(
    val details: Photo? = null,
    val shouldFinishScreen: Boolean = false,
    val error: TypedMessage? = null
)