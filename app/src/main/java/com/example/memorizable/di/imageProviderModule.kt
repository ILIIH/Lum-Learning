package com.example.memorizable.di

import com.example.core.DB.domain.photoLoader
import com.example.memorizable.data.photoLoaderImp
import org.koin.dsl.module

val imageProviderModule = module {
    single<photoLoader> {
        photoLoaderImp(get())
    }
}
