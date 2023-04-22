package com.pvsb.presentation.categories.allScreen.photoVault

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.presentation.R
import com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.PhotoDetailsActivity
import com.pvsb.presentation.ui.messageTextStyle
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.ui.titleTextStyle
import com.pvsb.presentation.utils.checkSelfPermissionCompat
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.FloatingAddButton
import com.pvsb.presentation.utils.getUriAccessPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoVaultActivity : ComponentActivity() {

    private val viewModel: PhotoVaultViewModel by viewModels()

    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPermissionRequestLauncher()

        setContent {

            val bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmValueChange = { true },
                skipHalfExpanded = true
            )

            ComposeAddPhotoBottomSheetOptions(
                modifier = Modifier.fillMaxSize(), state = bottomSheetState
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPhotos()
    }

    @Composable
    private fun ComposeContent(
        onAddClick: () -> Unit = {}
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
        ) {

            Column {
                BackButton {
                    finish()
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.session_option_label_photo_vault),
                        style = titleTextStyle
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    ComposeEmptyState(modifier = Modifier.fillMaxSize())
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingAddButton {
                    onAddClick()
                }
            }
        }
    }

    @Composable
    private fun ComposeEmptyState(
        modifier: Modifier = Modifier
    ) {

        Column(
            modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_empty_content), contentDescription = ""
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.empty_content_label),
                style = titleTextStyle,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.photo_vault_empty_content_message),
                color = AppColors.gray,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                style = messageTextStyle
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ComposeAddPhotoBottomSheetOptions(
        modifier: Modifier = Modifier,
        state: ModalBottomSheetState,
    ) {

        val scope = rememberCoroutineScope()

        Box(
            modifier = modifier, contentAlignment = Alignment.BottomCenter
        ) {

            ModalBottomSheetLayout(
                modifier = Modifier.fillMaxWidth(),
                sheetContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                    ) {
                        ComposeAddPhotoBottomSheetOptionsLayout()
                    }
                },
                sheetState = state,
                sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ) {
                ComposeContent {
                    scope.launch {
                        state.show()
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeAddPhotoBottomSheetOptionsLayout(
        modifier: Modifier = Modifier
    ) {

        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let(::getUriAccessPermission)
            viewModel.addPhoto(uri.toString())
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(4.dp)
                    .width(50.dp),
                backgroundColor = Color.White
            ) { }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(id = R.string.photo_vault_add_photo_options_bottom_sheet_label),
                style = titleTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(20.dp))

            ComposeAddPhotoBottomSheetOptionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                icon = R.drawable.ic_add_photo_by_camera,
                label = R.string.photo_vault_add_photo_option_bottom_sheet_by_camera_label,
                description = R.string.photo_vault_add_photo_option_bottom_sheet_by_camera_description
            ) {

                if (checkSelfPermissionCompat(Manifest.permission.CAMERA)) {
                    navigateToPhotoDetailsOrTakePicture()
                } else {
                    requestPermissionLauncher?.launch(Manifest.permission.CAMERA)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            ComposeAddPhotoBottomSheetOptionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                icon = R.drawable.ic_add_photo_by_gallery,
                label = R.string.photo_vault_add_photo_option_bottom_sheet_by_gallery_label,
                description = R.string.photo_vault_add_photo_option_bottom_sheet_by_gallery_description
            ) {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        }
    }

    @Composable
    private fun ComposeAddPhotoBottomSheetOptionButton(
        modifier: Modifier = Modifier,
        icon: Int = 0,
        label: Int = 0,
        description: Int = 0,
        onClick: () -> Unit = {}
    ) {
        Row(
            modifier = modifier.clickable {
                onClick()
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier.size(45.dp),
                backgroundColor = AppColors.lightBlue
            ) {

                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "option icon",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = stringResource(id = label), style = titleTextStyle, fontSize = 16.sp
                )

                Text(
                    text = stringResource(id = description), style = messageTextStyle
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }

    private fun navigateToPhotoDetailsOrTakePicture(
        photoId: Long? = null
    ) {
        val intent = Intent(this, PhotoDetailsActivity::class.java)

        photoId?.let {
            intent.putExtra(PhotoDetailsActivity.PHOTO_FROM_VAULT_ID_KEY, it)
        }

        startActivity(intent)
    }

    private fun registerPermissionRequestLauncher() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) navigateToPhotoDetailsOrTakePicture()
        }
    }

    @Preview
    @Composable
    fun ComposeAddPhotoBottomSheetOptionButtonPreview() {
        ComposeAddPhotoBottomSheetOptionButton(
            modifier = Modifier.fillMaxWidth(),
            icon = R.drawable.ic_add_photo_by_camera,
            label = R.string.photo_vault_add_photo_option_bottom_sheet_by_camera_label,
            description = R.string.photo_vault_add_photo_option_bottom_sheet_by_camera_description
        )
    }

    @Preview
    @Composable
    private fun ComposeAddPhotoBottomSheetOptionsLayoutPreview() {
        ComposeAddPhotoBottomSheetOptionsLayout(
            modifier = Modifier.wrapContentHeight(unbounded = true)
        )
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContent()
    }
}