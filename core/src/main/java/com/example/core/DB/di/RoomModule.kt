package com.example.core.DB.di

import androidx.room.Room
import com.example.core.DB.ThemeDatabace
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val RoomModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ThemeDatabace::class.java,
            "theme-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
