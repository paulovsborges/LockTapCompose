package com.pvsb.presentation.categories.favoriteScreen.photos

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pvsb.domain.entity.Photo
import com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.PhotoDetailsActivity
import com.pvsb.presentation.categories.favoriteScreen.ComposeEmptyQueryResults
import com.pvsb.presentation.ui.AppStyle

@Composable
fun CategoriesFavoritePhotosScreen(
    modifier: Modifier = Modifier, photos: List<Photo>
) {

    if (photos.isEmpty()) {
        ComposeEmptyQueryResults(modifier = Modifier.fillMaxSize())
    } else {

        Column(modifier.padding(top = 20.dp)) {
            ComposePhotoList(
                modifier = Modifier.fillMaxSize(), photos = photos
            )
        }
    }
}

@Composable
private fun ComposePhotoList(
    modifier: Modifier = Modifier, photos: List<Photo>
) {

    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), modifier = modifier.background(AppStyle.AppColors.background)
    ) {
        items(photos) {
            ComposePhotoCell(photo = it, modifier = Modifier, context = context)
        }
    }
}

@Composable
private fun ComposePhotoCell(
    modifier: Modifier = Modifier, photo: Photo, context: Context
) {

    Card(
        modifier = modifier
            .size(120.dp)
            .padding(1.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                }, indication = rememberRipple(bounded = false, radius = 50.dp)
            ) {
                navigateToPhotoDetails(photo.id, context)
            },
        shape = RoundedCornerShape(3.dp),
    ) {

        val painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
            .data(data = photo.imageFilePath).crossfade(true).build(), onError = {
            Log.d("", "### ${it.result.throwable.message}")
        })

        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .background(AppStyle.AppColors.translucent)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

private fun navigateToPhotoDetails(
    photoId: Long, context: Context
) {
    val intent = Intent(context, PhotoDetailsActivity::class.java)
    intent.putExtra(PhotoDetailsActivity.PHOTO_FROM_VAULT_ID_KEY, photoId)
    context.startActivity(intent)
}

@Preview
@Composable
private fun CategoriesFavoritePhotosScreenPreview() {

    val photos = List(5) {
        Photo(it.toLong(), "", true)
    }

    CategoriesFavoritePhotosScreen(photos = photos)
}