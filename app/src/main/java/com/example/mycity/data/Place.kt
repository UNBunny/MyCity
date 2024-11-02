package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Place(
    val id: Long,
    @StringRes val name : Int,
    @StringRes val description : Int,
    @DrawableRes val img : Int,
    val type: CategoryType // Add the CategoryType field,
)
