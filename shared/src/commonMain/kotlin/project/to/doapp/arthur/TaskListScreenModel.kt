package project.to.doapp.arthur

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import project.to.doapp.arthur.database.TaskEntity

class TaskListScreenModel(private val repository: TaskRepository) : ScreenModel {

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    init {
        loadTasks()
    }

    fun loadTasks() {
        screenModelScope.launch {
            // Vai à base de dados buscar as tarefas e atualiza a UI
            _tasks.value = repository.getAllTasks()
        }
    }
}