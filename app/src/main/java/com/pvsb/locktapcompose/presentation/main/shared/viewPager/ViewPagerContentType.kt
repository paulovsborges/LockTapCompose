package com.pvsb.locktapcompose.presentation.main.shared.viewPager

import androidx.annotation.StringRes
import com.pvsb.locktapcompose.R

sealed class ViewPagerContentType(
    @StringRes
    val label: Int
){
    object All: ViewPagerContentType(R.string.view_pager_label_all)
    object Favorites: ViewPagerContentType(R.string.view_pager_label_favorites)
}
