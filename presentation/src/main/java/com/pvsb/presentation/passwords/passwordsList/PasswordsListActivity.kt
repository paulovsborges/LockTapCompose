package com.pvsb.presentation.passwords.passwordsList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.domain.entity.Password
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.messageTextStyle
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.ui.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.FloatingAddButton
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField
import com.pvsb.presentation.utils.formatToStringDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class PasswordsListActivity : ComponentActivity() {

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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingAddButton {}
            }

            Column() {
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
                        text = stringResource(id = R.string.session_option_label_wifi_passwords),
                        style = titleTextStyle
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    ComposePrimarySearchField()

                    ComposeEmptyState(modifier = Modifier.fillMaxSize())
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
                text = stringResource(id = R.string.wifi_passwords_empty_content_message),
                color = AppColors.gray,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                style = messageTextStyle
            )
        }
    }

    @Composable
    private fun ComposePasswordCard(
        modifier: Modifier = Modifier,
        password: Password
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = AppColors.secondary
        ) {
            Box() {

                Column(
                    modifier = Modifier.padding(top = 12.dp, start = 15.dp)
                ) {
                    Text(
                        text = "Title",
                        color = AppColors.gray,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = password.title,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Password",
                        color = AppColors.gray,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = password.password,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {

                        Row(modifier = Modifier.fillMaxWidth()) {

                            Icon(
                                painter = painterResource(id = R.drawable.ic_copy),
                                contentDescription = "",
                                tint = AppColors.gray,
                                modifier = Modifier.clickable {
//                            context.copyTextToClipBoard(contactData.phoneNumber)
                                }
                            )

                            Spacer(modifier = Modifier.width(5.dp))

//                        stringResource(id = R.string.copy_password)

                            Text(
                                text = "Copy password",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                                color = AppColors.gray
                            )
                        }

                        Text(
                            text = password.createdAt?.formatToStringDate() ?: "",
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            color = AppColors.gray,
                            fontSize = 14.sp,
                            modifier = Modifier.alpha(0.6f)
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    ComposeFavoriteButton(
                        isFavorite = password.isFavorite
                    ) {

                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeFavoriteButton(
        modifier: Modifier = Modifier,
        isFavorite: Boolean = false,
        onFavoriteClicked: (Boolean) -> Unit = {}
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 28.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier.clickable {
                    onFavoriteClicked(!isFavorite)
                }
            ) {
                if (isFavorite) {

                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "",
                        tint = AppColors.lightBlue
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ComposePasswordCardPreview() {
        Box(modifier = Modifier.padding(20.dp)) {
            ComposePasswordCard(
                password = Password(
                    "",
                    "Home wifi",
                    "123456",
                    null,
                    true,
                    null
                )
            )
        }
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContent()
    }
}
