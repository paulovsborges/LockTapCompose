package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import com.pvsb.domain.entity.Photo
import com.pvsb.domain.entity.TypedMessage

data class PhotoDetailsState(
    val details: Photo? = null,
    val shouldFinishScreen: Boolean = false,
    val screenType: ScreenType = ScreenType.TakePhoto,
    val error: TypedMessage? = null
)

sealed interface ScreenType {
    object TakePhoto : ScreenType
    data class PhotoDetails(val photoId: Long) : ScreenType
}
