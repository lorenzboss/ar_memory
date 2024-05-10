package ch.bfh.teamulrich.memory

import androidx.annotation.DrawableRes

sealed class Screen(val route: String, @DrawableRes val icon: Int) {
    object Reader : Screen("Reader", R.drawable.ic_reader)
}