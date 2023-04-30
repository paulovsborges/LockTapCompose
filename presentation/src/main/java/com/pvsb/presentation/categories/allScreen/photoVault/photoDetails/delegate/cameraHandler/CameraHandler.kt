package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.delegate.cameraHandler

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat

class CameraHandler : ICameraHandler {

    private var isLensFacingBack: Boolean = true
    private var previewView: PreviewView? = null

    override fun ComponentActivity.startCamera(
        previewView: PreviewView
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        this@CameraHandler.previewView = previewView

        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()

                val lensFacing = if (isLensFacingBack) {
                    CameraSelector.LENS_FACING_BACK
                } else {
                    CameraSelector.LENS_FACING_FRONT
                }

                val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

                val preview = this@CameraHandler.previewView?.let { preview ->
                    androidx.camera.core.Preview.Builder().build().also {
                        it.setSurfaceProvider(preview.surfaceProvider)
                    }
                } ?: return@addListener

                val imageCapture = ImageCapture.Builder().build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(this)
            )
        }

        override fun ComponentActivity.toggleLensFacing(
            previewView: PreviewView
        ) {
            isLensFacingBack = !isLensFacingBack
            startCamera(previewView)
        }

        override fun getBitMapFromPreview(): Bitmap? {
            return previewView?.bitmap
        }
    }
    