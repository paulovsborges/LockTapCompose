package com.pvsb.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.copyTextToClipBoard(
    text: String,
) {

    val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val data = ClipData.newPlainText("text", text)
    manager.setPrimaryClip(data)
    Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
}

fun Context.getUriAccessPermission(uri: Uri) {

    val takeFlags: Int =
        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

    contentResolver.takePersistableUriPermission(uri, takeFlags)
}

fun Context.checkSelfPermissionCompat(permission: String) = ActivityCompat.checkSelfPermission(
    this, permission
) == PackageManager.PERMISSION_GRANTED

