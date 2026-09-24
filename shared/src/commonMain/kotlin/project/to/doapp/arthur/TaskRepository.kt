package project.to.doapp.arthur

import project.to.doapp.arthur.database.AppDatabase
import project.to.doapp.arthur.database.CategoryEntity
import project.to.doapp.arthur.database.TaskEntity

class TaskRepository(databaseDriverFactory: DatabaseDriverFactory) {

    // Inicializa a base de dados com o driver específico da plataforma
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val queries = database.appDatabaseQueries

    // --- Operações de Tarefas ---

    fun getAllTasks(): List<TaskEntity> {
        return queries.selectAllTasks().executeAsList()
    }

    // --- Operações de Categorias ---

    fun insertCategory(name: String) {
        queries.insertCategory(name)
    }

    fun getAllCategories(): List<CategoryEntity> {
        return queries.selectAllCategories().executeAsList()
    }
}