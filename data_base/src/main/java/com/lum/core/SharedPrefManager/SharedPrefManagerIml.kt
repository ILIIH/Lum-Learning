package com.lum.core.SharedPrefManager

import android.content.Context
import android.content.SharedPreferences


class SharedPrefManagerIml(context: Context) : com.lum.core.SharedPrefManager.SharedPrefManager {

    var sharedPreferences: SharedPreferences = context.getSharedPreferences("LLPreferences", Context.MODE_PRIVATE)

    override fun setSkipCardDescription(cardType: String) {
        sharedPreferences.edit().apply {
            putBoolean("Skip$cardType", true)
            apply()
        }
    }

    override fun doSkipCardDescription(cardType: String) = sharedPreferences.getBoolean("Skip$cardType", false)

}