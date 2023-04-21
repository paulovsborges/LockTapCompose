package com.pvsb.presentation.categories.allScreen.photoVault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.presentation.R
import com.pvsb.presentation.passwords.passwordsList.PasswordsListState
import com.pvsb.presentation.ui.messageTextStyle
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.ui.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.FloatingAddButton
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField

class PhotoVaultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeContent()
        }
    }

    @Composable
    private fun ComposeContent() {

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

    @Preview
    @Composable
    fun ComposeContentPreview(){
        ComposeContent()
    }
}