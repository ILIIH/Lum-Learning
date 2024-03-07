package com.example.plain_domain.di

import com.example.plain_data.TasksRepository
import com.example.plain_domain.TasksRepositoryImp
import org.koin.dsl.module


val plainDomainModule = module {

    single<TasksRepository> {
        TasksRepositoryImp(get(), get())
    }
}