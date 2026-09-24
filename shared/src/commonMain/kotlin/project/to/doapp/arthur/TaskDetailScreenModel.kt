package project.to.doapp.arthur

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class TaskDetailScreenModel(private val repository: TaskRepository) : ScreenModel {

    fun saveTask(title: String, description: String, onSaveComplete: () -> Unit) {
        screenModelScope.launch {
            // Só guarda a descrição se não estiver vazia
            val finalDesc = description.takeIf { it.isNotBlank() }
            repository.insertTask(title, finalDesc)

            // Avisa a UI que terminou de guardar
            onSaveComplete()
        }
    }
}