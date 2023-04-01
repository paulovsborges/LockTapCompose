package com.pvsb.locktapcompose.presentation.utils.components.viewPager

import androidx.annotation.StringRes
import com.pvsb.locktapcompose.R

sealed class ViewPagerContentType(
    @StringRes
    val label: Int
){
    object All: ViewPagerContentType(R.string.view_pager_label_all)
    object Favorites: ViewPagerContentType(R.string.view_pager_label_favorites)
    object Contacts: ViewPagerContentType(R.string.view_pager_label_contacts)
    object WifiPass: ViewPagerContentType(R.string.view_pager_label_wifi_pass)
    object Photos: ViewPagerContentType(R.string.view_pager_label_photos)

}
