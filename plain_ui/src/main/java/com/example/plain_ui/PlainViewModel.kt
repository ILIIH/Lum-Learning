package com.example.plain_ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plain_data.Task
import com.example.plain_data.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

//TODO: instead of repository we should use useCases
class PlainViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    private val _tasks =  MutableStateFlow<List<Task>?>(null)
    val tasks: StateFlow<List<Task>?>
        get() = _tasks

    init {
        viewModelScope.launch {
            _tasks.tryEmit(tasksRepository.getAllTasks().filterNotNull())
        }

    }
}