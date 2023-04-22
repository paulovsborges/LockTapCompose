package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.utils.components.BackButton

class PhotoDetailsActivity : ComponentActivity(),
    ICameraHandler by CameraHandler() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeScreenLayout()
        }
    }

    @Composable
    private fun ComposeScreenLayout() {

        val photoId = intent.getLongExtra(
            PHOTO_FROM_VAULT_ID_KEY, DEFAULT_PHOTO_FROM_VAULT_ID
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
        ) {

            BackButton {
                finish()
            }

            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
            ) {

                if (photoId != DEFAULT_PHOTO_FROM_VAULT_ID) {
                    // set image from vault
                    ComposeImageDetails()
                } else {
                    // init camera
                    InitCameraPreview()
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
                            ), alpha = 0.15f
                        )
                ) {

                    ComposeDetailsImageOptions(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 12.dp, start = 80.dp, end = 80.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    @Composable
    private fun InitCameraPreview() {

        val preview by remember {
            mutableStateOf(PreviewView(this))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(630.dp)
        ) {
            AndroidView(factory = {
                preview
            })
        }

        startCamera(preview)
    }

    @Composable
    private fun ComposeImageDetails() {

        ComposeImage(
            imagePath = "", modifier = Modifier
                .fillMaxWidth()
                .height(630.dp)
        )
    }

    @Composable
    private fun ComposeDetailsImageOptions(modifier: Modifier = Modifier) {

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

            }

            Column(
                modifier = Modifier.clickable {

                },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                ComposeFavoriteButton()

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

            }
        }
    }

    @Composable
    private fun ComposeFavoriteButton(
        modifier: Modifier = Modifier,
        isFavorite: Boolean = false,
        onFavoriteClicked: (Boolean) -> Unit = {}
    ) {
        Box(modifier = modifier.clickable {
            onFavoriteClicked(!isFavorite)
        }) {
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
        modifier: Modifier = Modifier, icon: Int, label: Int, onClick: () -> Unit
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
        imagePath: String
    ) {

        val painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
            .data(data = "content://com.android.providers.media.documents/document/image%3A59")
            .build(), onError = {
            Log.d("", "### ${it.result.throwable.message}")
        })

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