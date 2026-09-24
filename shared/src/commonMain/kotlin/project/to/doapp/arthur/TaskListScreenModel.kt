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
        // A corrotina fica a escutar a base de dados permanentemente
        screenModelScope.launch {
            repository.getAllTasks().collect { novaLista ->
                _tasks.value = novaLista
            }
        }
    }

    fun toggleTaskCompletion(task: TaskEntity) {
        screenModelScope.launch {
            repository.updateTaskCompletion(task.id, !task.completed)

            // Não precisamos mais do loadTasks() aqui!
        }
    }

    fun deleteTask(taskId: Long) {
        screenModelScope.launch {
            repository.deleteTask(taskId)

            // Nem precisamos do loadTasks() aqui!
        }
    }
}