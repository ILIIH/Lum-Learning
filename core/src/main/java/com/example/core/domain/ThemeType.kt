package com.example.core.domain

sealed class ThemeType(val num: Int, val name: String) {
    object REGULAR_THEME : ThemeType(1, "Regular theme")
    object OPEN_THEME : ThemeType(2, "Open theme")
    object THEME_FORMULA : ThemeType(3, "Theme formula")
    object THEME_MIX : ThemeType(4, "Theme mix")

    companion object {
        fun fromInt(value: Int): ThemeType {
            return when (value) {
                1 -> REGULAR_THEME
                2 -> OPEN_THEME
                3 -> THEME_FORMULA
                4 -> THEME_MIX
                else -> throw IllegalArgumentException("Invalid ThemeType value: $value")
            }
        }
    }
}
