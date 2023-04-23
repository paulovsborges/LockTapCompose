package com.pvsb.presentation.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

//
//
//fun saveBitMapToFile(bitMap: Bitmap) {
//
//    try {
//        val root = Environment.getExternalStoragePublicDirectory(
//            Environment.DIRECTORY_PICTURES
//        ).toString()
//
//        val myDir = File("$root/saved_images")
//
//        myDir.mkdirs()
////        if (!myDir.exists()) {
////            try {
////                myDir.mkdirs()
////            } catch (e: Exception) {
////                e.printStackTrace()
////            }
////        }
//
//        val fileName = System.currentTimeMillis().toString().replace(":", ".") + ".jpg"
//
//        val newFile = File(myDir, fileName)
//
//        val outStream = FileOutputStream(newFile)
//
//        bitMap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
//
//        Log.d("", "## uri ${newFile.absolutePath}")
//
//        outStream.flush()
//        outStream.close()
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}

fun Context.saveBitMapToFile(bitmap: Bitmap): Uri? {

    var uri: Uri? = null

    val name = System.currentTimeMillis().toString()

    val folderName = "locktap"

    val fos: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/$folderName")
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let {
            uri = it
            contentResolver.openOutputStream(it)
        }
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM
        ).toString() + File.separator + folderName
        val file = File(imagesDir)
        if (!file.exists()) {
            file.mkdir()
        }
        val image = File(imagesDir, "$name.png")
        uri = image.toUri()
        FileOutputStream(image)
    }
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)

    fos?.flush()
    fos?.close()

    return uri
}