package com.lum.core.SharedPrefManager

interface SharedPrefManager {
    fun setSkipCardDescription(cardType: String)
    fun doSkipCardDescription(cardType: String): Boolean
}