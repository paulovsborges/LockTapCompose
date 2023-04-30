package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pvsb.domain.entity.Photo
import com.pvsb.presentation.R
import com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.delegate.cameraHandler.CameraHandler
import com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.delegate.cameraHandler.ICameraHandler
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeErrorCard
import com.pvsb.presentation.utils.saveBitMapToFile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailsActivity : ComponentActivity(), ICameraHandler by CameraHandler() {

    enum class ScreenType {
        PHOTO_DETAILS,
        TAKE_PICTURE
    }

    private val viewModel: PhotoDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photoId = intent.getLongExtra(
            PHOTO_FROM_VAULT_ID_KEY, DEFAULT_PHOTO_FROM_VAULT_ID
        )

        val screenType = if (photoId != DEFAULT_PHOTO_FROM_VAULT_ID) {
            viewModel.getPhotoDetails(photoId)
            ScreenType.PHOTO_DETAILS
        } else {
            ScreenType.TAKE_PICTURE
        }

        setContent {

            val state = viewModel.state.collectAsState()

            ComposeScreenLayout(
                state = state.value,
                screenType = screenType
            )
        }
    }

    @Composable
    private fun ComposeScreenLayout(
        state: PhotoDetailsState = PhotoDetailsState(),
        screenType: ScreenType = ScreenType.PHOTO_DETAILS
    ) {

        if (state.shouldFinishScreen) {
            finish()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
        ) {

            BackButton {
                finish()
            }

            ComposeErrorCard(
                isErrorVisible = state.error != null,
                error = state.error,
                modifier = Modifier
                    .padding(20.dp)
                    .clickable {
                        viewModel.dismissError()
                    }
            )

            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
            ) {

                when (screenType) {
                    ScreenType.PHOTO_DETAILS -> {
                        state.details?.imageFilePath?.let { imagePath ->
                            ComposeImage(
                                imagePath = imagePath,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(630.dp)
                            )
                        }
                    }
                    ScreenType.TAKE_PICTURE -> {
                        InitCameraPreview()
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    AppColors.gray, AppColors.gray
                                )
                            ),
                            alpha = 0.15f
                        )
                ) {

                    if (screenType == ScreenType.PHOTO_DETAILS) {
                        state.details?.let {
                            ComposeImageDetailsOptions(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(top = 12.dp, start = 80.dp, end = 80.dp),
                                state.details
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }

    @Composable
    private fun InitCameraPreview() {

        val preview by remember {
            mutableStateOf(PreviewView(this))
        }

        var imageBitMap by remember {
            mutableStateOf<Bitmap?>(null)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            if (imageBitMap == null) {
                AndroidView(
                    factory = {
                        preview
                    }, modifier = Modifier.fillMaxSize()
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_shoot_photo),
                        contentDescription = stringResource(id = R.string.button_take_photo),
                        tint = Color.White,
                        modifier = Modifier
                            .size(80.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = rememberRipple(bounded = false, radius = 20.dp)
                            ) {
                                imageBitMap = getBitMapFromPreview()
                            }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_change_camera_lens),
                            contentDescription = stringResource(id = R.string.button_turn_facing_camera),
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = rememberRipple(bounded = false, radius = 20.dp)
                                ) {
                                    toggleLensFacing(preview)
                                }
                        )
                    }
                } else {
                    ComposeImage(modifier = Modifier.fillMaxSize(), imagePath = imageBitMap!!)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(
                            20.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_try_again),
                            contentDescription = stringResource(id = R.string.button_try_again_take_photo),
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = rememberRipple(bounded = false, radius = 20.dp)
                                ) {
                                    imageBitMap = null
                                }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_confirm),
                            contentDescription = stringResource(id = R.string.button_confirm),
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = rememberRipple(bounded = false, radius = 20.dp)
                                ) {
                                    val uri = imageBitMap?.let(::saveBitMapToFile)
                                    viewModel.addPhoto(uri.toString())
                                }
                        )
                    }
                }
            }

            startCamera(preview)
        }

        @Composable
        private fun ComposeImageDetailsOptions(
            modifier: Modifier = Modifier,
            photoDetails: Photo
        ) {

            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(
                    50.dp, Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.Bottom
            ) {

                ComposeImageOption(
                    icon = R.drawable.ic_share,
                    label = R.string.photo_vault_details_share_label,
                ) {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, photoDetails.imageFilePath.toUri())
                        type = "image/png"
                    }

                    startActivity(shareIntent)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    ComposeFavoriteButton(isFavorite = photoDetails.isFavorite) {
                        viewModel.togglePhotoFavorite()
                    }

                    Text(
                        text = stringResource(id = R.string.photo_vault_details_favorite_label),
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular))
                    )
                }

                ComposeImageOption(
                    icon = R.drawable.ic_delete,
                    label = R.string.photo_vault_details_delete_label,
                ) {
                    viewModel.deletePhoto()
                }
            }
        }

        @Composable
        private fun ComposeFavoriteButton(
            modifier: Modifier = Modifier,
            isFavorite: Boolean = false,
            onFavoriteClicked: (Boolean) -> Unit = {}
        ) {
            Box(
                modifier = modifier.clickable {
                    onFavoriteClicked(!isFavorite)
                }
            ) {
                if (isFavorite) {

                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "",
                        tint = AppColors.lightBlue
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }

        @Composable
        private fun ComposeImageOption(
            modifier: Modifier = Modifier,
            icon: Int,
            label: Int,
            onClick: () -> Unit
        ) {

            Column(
                modifier = modifier.clickable { onClick() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = icon), contentDescription = "", tint = Color.White
                )
                Text(
                    text = stringResource(id = label),
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.sf_pro_display_regular))
                )
            }
        }

        @Composable
        private fun ComposeImage(
            modifier: Modifier = Modifier,
            imagePath: Any
        ) {

            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imagePath)
                    .crossfade(true)
                    .build(),
                onError = {
                    Log.d("", "### ${it.result.throwable.message}")
                }
            )

            Image(
                painter = painter,
                contentDescription = "",
                modifier = modifier.background(AppColors.translucent),
                contentScale = ContentScale.Crop
            )
        }

        @Preview
        @Composable
        private fun ComposeContentPreview() {
            ComposeScreenLayout()
        }

        companion object {
            const val PHOTO_FROM_VAULT_ID_KEY = "PHOTO_FROM_VAULT_ID_KEY"
            const val DEFAULT_PHOTO_FROM_VAULT_ID = -1L
        }
    }
    