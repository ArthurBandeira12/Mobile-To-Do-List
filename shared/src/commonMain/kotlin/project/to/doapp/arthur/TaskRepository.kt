package project.to.doapp.arthur

import project.to.doapp.arthur.database.AppDatabase
import project.to.doapp.arthur.database.CategoryEntity
import project.to.doapp.arthur.database.TaskEntity
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TaskRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val queries = database.appDatabaseQueries

    // --- Operações de Tarefas ---

    // Agora retorna um Flow contínuo em vez de uma lista estática
    fun getAllTasks(): Flow<List<TaskEntity>> {
        // Dispatchers.Default garante que a leitura da DB não bloqueia a UI
        return queries.selectAllTasks().asFlow().mapToList(Dispatchers.Default)
    }

    fun insertTask(title: String, description: String?) {
        queries.insertTask(title, description, "Hoje")
    }

    fun updateTaskCompletion(id: Long, completed: Boolean) {
        queries.updateTaskCompletion(completed, id)
    }

    fun deleteTask(id: Long) {
        queries.deleteTask(id)
    }

    // --- Operações de Categorias ---

    fun insertCategory(name: String) {
        queries.insertCategory(name)
    }

    fun getAllCategories(): List<CategoryEntity> {
        return queries.selectAllCategories().executeAsList()
    }

    fun getTaskById(id: Long): TaskEntity? {
        return queries.selectTaskById(id).executeAsOneOrNull()
    }

    fun updateTaskText(id: Long, title: String, description: String?) {
        queries.updateTaskText(title, description, id)
    }
}