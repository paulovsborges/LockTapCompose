package com.pvsb.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

fun Context.copyTextToClipBoard(
    text: String,
) {

    val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val data = ClipData.newPlainText("text", text)
    manager.setPrimaryClip(data)
    Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
}