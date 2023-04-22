package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.camera.view.PreviewView

interface ICameraHandler {

    fun ComponentActivity.startCamera(
        previewView: PreviewView,
        lensFacingBack: Boolean = true
    )

    fun ComponentActivity.takePhoto(onPhotoSaved: (Uri) -> Unit)
}