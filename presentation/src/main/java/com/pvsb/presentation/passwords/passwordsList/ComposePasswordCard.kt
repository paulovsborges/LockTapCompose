package com.pvsb.presentation.passwords.passwordsList

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.domain.entity.Password
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle
import com.pvsb.presentation.utils.formatToStringDate

@Composable
fun ComposePasswordCard(
    modifier: Modifier = Modifier,
    password: Password,
    onCardClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    onCopyPassword: (String) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(unbounded = true)
            .clickable {
                onCardClick(password.id)
            },
        shape = RoundedCornerShape(8.dp), backgroundColor = AppStyle.AppColors.secondary
    ) {
        Box {

            Column(
                modifier = Modifier.padding(top = 12.dp, start = 15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.password_card_title_label),
                    color = AppStyle.AppColors.gray,
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
                    text = stringResource(id = R.string.password_card_password_label),
                    color = AppStyle.AppColors.gray,
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCopyPassword(password.password)
                            }
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = "",
                            tint = AppStyle.AppColors.gray
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = stringResource(id = R.string.copy_password),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            color = AppStyle.AppColors.gray
                        )
                    }

                    Text(
                        text = password.createdAt?.formatToStringDate() ?: "",
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        color = AppStyle.AppColors.gray,
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
                    onFavoriteClick(password.id)
                }
            }
        }
    }
}

@Composable
fun ComposeFavoriteButton(
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
                    tint = AppStyle.AppColors.lightBlue
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
        ComposePasswordCard(modifier = Modifier, password = Password(
            "", "Home wifi", "123456", null, true, null
        ), {}, {}, {})
    }
}