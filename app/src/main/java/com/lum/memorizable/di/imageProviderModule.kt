package com.lum.memorizable.di

import com.lum.core.data.PhotoManager
import com.lum.core.data.PhotoManagerImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageProviderModule = module {
    single <PhotoManager>{
        PhotoManagerImp(androidContext())
    }
}
