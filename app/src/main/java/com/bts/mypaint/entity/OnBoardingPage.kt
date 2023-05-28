package com.bts.mypaint.entity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bts.mypaint.R

enum class OnBoardingPage(
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int,
    @DrawableRes val logoResource: Int
) {

    ONE(R.string.onboard_first_title, R.string.onboard_first_details, R.drawable.logo),
    TWO(R.string.onboard_second_title, R.string.onboard_second_details, R.drawable.logo),
    THREE(R.string.onboard_third_title, R.string.onboard_third_details, R.drawable.logo)

}