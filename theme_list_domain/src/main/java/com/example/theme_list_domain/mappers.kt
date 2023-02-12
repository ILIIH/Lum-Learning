package com.example.theme_list_domain

import com.example.theme_list_data.Theme

fun ThemeDomain.toData() = Theme(this.Title, this.ImageUri)
