package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import android.os.Bundle
import androidx.activity.ComponentActivity

class PhotoDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photoId = intent.getLongExtra(
            PHOTO_FROM_VAULT_ID_KEY,
            DEFAULT_PHOTO_FROM_VAULT_ID
        )

        if (photoId != DEFAULT_PHOTO_FROM_VAULT_ID) {
            // set image from vault
        }else{
            // init camera
        }

    }

    companion object {
        const val PHOTO_FROM_VAULT_ID_KEY = "PHOTO_FROM_VAULT_ID_KEY"
        const val DEFAULT_PHOTO_FROM_VAULT_ID = -1L
    }
}