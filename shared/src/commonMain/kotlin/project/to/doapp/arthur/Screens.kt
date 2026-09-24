package project.to.doapp.arthur

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
// Ecrã 1 - Lista de Tarefas
class TaskListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        // Recuperamos o repositório invisível e passamos para o Model
        val repository = LocalTaskRepository.current
        val screenModel = rememberScreenModel { TaskListScreenModel(repository) }

        // Observamos a lista de tarefas reativamente
        val tasks by screenModel.tasks.collectAsState()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { navigator.push(TaskDetailScreen(taskId = null)) }) {
                    Text("+")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
            ) {
                Text("As Minhas Tarefas", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))

                if (tasks.isEmpty()) {
                    Text("Sem tarefas! Pressione o '+' para criar uma.")
                } else {
                    LazyColumn {
                        items(tasks) { task ->
                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(task.title, style = MaterialTheme.typography.titleMedium)
                                    if (task.description != null) {
                                        Text(task.description, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Ecrã 2 - Detalhe/Edição da Tarefa
// Recebe o ID opcional. Se for nulo, é criação. Se tiver ID, é edição.
data class TaskDetailScreen(val taskId: Long?) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(if (taskId == null) "Ecrã 2: Nova Tarefa" else "Ecrã 2: Editar Tarefa $taskId")
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navigator.pop() }) {
                Text("Voltar à Lista")
            }
        }
    }
}

// Ecrã 3 - Gestão de Categorias
class CategoryManagementScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Ecrã 3: Gestão de Categorias")
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navigator.pop() }) {
                Text("Voltar à Lista")
            }
        }
    }
}