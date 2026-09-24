package project.to.doapp.arthur

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import project.to.doapp.arthur.database.TaskEntity
import androidx.compose.runtime.LaunchedEffect

class TaskDetailScreenModel(private val repository: TaskRepository) : ScreenModel {

    // Lê a tarefa antiga da base de dados
    fun getTask(taskId: Long): TaskEntity? {
        return repository.getTaskById(taskId)
    }

    // Grava (Update se tiver ID, Insert se não tiver)
    fun saveTask(taskId: Long?, title: String, description: String, onSaveComplete: () -> Unit) {
        screenModelScope.launch {
            val finalDesc = description.takeIf { it.isNotBlank() }

            if (taskId == null) {
                repository.insertTask(title, finalDesc)
            } else {
                repository.updateTaskText(taskId, title, finalDesc)
            }

            onSaveComplete()
        }
    }
}