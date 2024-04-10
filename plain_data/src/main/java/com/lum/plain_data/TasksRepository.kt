package com.lum.plain_data

interface TasksRepository {
    suspend fun getAllTasks() : List<Task?>
}