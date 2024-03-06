package com.example.core.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view: View? = activity.currentFocus
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}