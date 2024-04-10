package com.lum.add_new_card_domain.di

import com.lum.add_new_card_data.CardRepository
import com.lum.add_new_card_domain.CardRepositoryImp
import org.koin.dsl.module

val cardModule = module {

    single<CardRepository> {
        CardRepositoryImp(get())
    }
}
