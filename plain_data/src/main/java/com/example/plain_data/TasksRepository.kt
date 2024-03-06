package com.example.plain_data

interface TasksRepository {
    suspend fun getAllTasks() : List<Task?>
}