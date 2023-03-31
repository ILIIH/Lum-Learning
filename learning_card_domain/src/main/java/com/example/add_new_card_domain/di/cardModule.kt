package com.example.add_new_card_domain.di

import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_domain.CardRepositoryImp
import org.koin.dsl.module

val cardModule = module {

    single<CardRepository> {
        CardRepositoryImp(get())
    }
}
