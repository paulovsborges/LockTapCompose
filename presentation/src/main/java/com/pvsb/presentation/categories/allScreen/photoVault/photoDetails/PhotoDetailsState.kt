package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import com.pvsb.domain.entity.Photo

data class PhotoDetailsState(
    val details: Photo? = null,
    val shouldFinishScreen: Boolean = false,
    val screenType: ScreenType = ScreenType.TakePhoto
)

sealed interface ScreenType {
    object TakePhoto : ScreenType
    data class PhotoDetails(val photoId: Long) : ScreenType
}
