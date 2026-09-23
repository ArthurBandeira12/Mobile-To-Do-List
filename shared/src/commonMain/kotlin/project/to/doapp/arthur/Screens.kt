package project.to.doapp.arthur

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

// Ecrã 1 - Lista de Tarefas
class TaskListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Ecrã 1: Lista de Tarefas")
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navigator.push(TaskDetailScreen(taskId = null)) }) {
                Text("Criar Nova Tarefa")
            }

            Button(onClick = { navigator.push(CategoryManagementScreen()) }) {
                Text("Gerir Categorias")
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