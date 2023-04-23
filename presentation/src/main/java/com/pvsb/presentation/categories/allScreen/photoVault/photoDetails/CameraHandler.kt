package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Locale

class CameraHandler : ICameraHandler {

    private var imageCapture: ImageCapture? = null
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

//                val preview = androidx.camera.core.Preview.Builder().build().also {
//                    it.setSurfaceProvider(this@CameraHandler.previewView!!.surfaceProvider)
//                }

                imageCapture = ImageCapture.Builder().build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(this)
        )
    }

    override fun ComponentActivity.takePhoto(onPhotoSaved: (Bitmap) -> Unit) {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(
            FILENAME_FORMAT, Locale.ENGLISH
        ).format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

//        imageCapture.takePicture(
//            ContextCompat.getMainExecutor(this),
//            @ExperimentalGetImage object : ImageCapture.OnImageCapturedCallback() {
//                override fun onCaptureSuccess(proxy: ImageProxy) {
////                    val bitMap = BitmapUtils.getBitmap(proxy)
////
////                    val bitMap = proxy.image?.toBitmap()
////                    bitMap?.let(onPhotoSaved)
//
//
//
//
//                    proxy.close()
//                }
//            })

//        imageCapture.takePicture(outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    outputFileResults.savedUri?.let(onPhotoSaved)
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//                    exception.printStackTrace()
//                }
//            })
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

    private companion object {
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}