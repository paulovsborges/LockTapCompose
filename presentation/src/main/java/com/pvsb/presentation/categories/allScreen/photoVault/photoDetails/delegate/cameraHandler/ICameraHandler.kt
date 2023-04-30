package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.delegate.cameraHandler

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.camera.view.PreviewView

interface ICameraHandler {
    fun ComponentActivity.startCamera(previewView: PreviewView)
    fun ComponentActivity.toggleLensFacing(previewView: PreviewView)
    fun getBitMapFromPreview(): Bitmap?
}
