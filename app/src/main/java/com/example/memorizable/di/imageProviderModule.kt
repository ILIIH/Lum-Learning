package com.example.memorizable.di

import com.example.core.data.PhotoManager
import com.example.core.data.PhotoManagerImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageProviderModule = module {
    single <PhotoManager>{
        PhotoManagerImp(androidContext())
    }
}
