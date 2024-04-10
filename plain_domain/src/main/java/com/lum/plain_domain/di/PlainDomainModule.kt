package com.lum.plain_domain.di

import com.lum.plain_data.TasksRepository
import com.lum.plain_domain.TasksRepositoryImp
import org.koin.dsl.module


val plainDomainModule = module {

    single<TasksRepository> {
        TasksRepositoryImp(get(), get())
    }
}