package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlin.coroutines.suspendCoroutine

class CameraHandler : ICameraHandler {

    /*
    @Composable
    fun ComponentActivity.CameraView(
        outputDirectory: File,
        executor: Executor,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        // 1
        val lensFacing = CameraSelector.LENS_FACING_BACK
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        val preview = Preview.Builder().build()
        val previewView = remember { PreviewView(context) }
        val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()

        LaunchedEffect(lensFacing) {
            val cameraProvider = getCameraProvider()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )

            preview.setSurfaceProvider(previewView.surfaceProvider)
        }

        // 3
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

//            IconButton(
//                modifier = Modifier.padding(bottom = 20.dp),
//                onClick = {
//                    Log.i("kilo", "ON CLICK")
//                    takePhoto(
//                        filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
//                        imageCapture = imageCapture,
//                        outputDirectory = outputDirectory,
//                        executor = executor,
//                        onImageCaptured = onImageCaptured,
//                        onError = onError
//                    )
//                },
//                content = {
//                    Icon(
//                        imageVector = Icons.Sharp.Lens,
//                        contentDescription = "Take picture",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(100.dp)
//                            .padding(1.dp)
//                            .border(1.dp, Color.White, CircleShape)
//                    )
//                }
//            )
        }
    }

     */

    override fun ComponentActivity.startCamera(
        previewView: PreviewView,
        lensFacingBack: Boolean
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()

                val lensFacing = if (lensFacingBack) {
                    CameraSelector.LENS_FACING_BACK
                } else {
                    CameraSelector.LENS_FACING_FRONT
                }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(lensFacing)
                    .build()

                val preview = androidx.camera.core.Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val imageCapture = ImageCapture.Builder().build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    private suspend fun ComponentActivity.getCameraProvider(): ProcessCameraProvider {
        return suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this).also { cameraProvider ->
                cameraProvider.addListener({

                    continuation.resumeWith(
                        Result.success(cameraProvider.get())
                    )

                }, ContextCompat.getMainExecutor(this))
            }
        }
    }
}