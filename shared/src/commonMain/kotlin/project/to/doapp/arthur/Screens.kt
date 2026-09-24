package project.to.doapp.arthur
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import cafe.adriel.voyager.core.model.rememberScreenModel
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class TaskDetailScreen(val taskId: Long? = null) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val repository = LocalTaskRepository.current
        val screenModel = rememberScreenModel { TaskDetailScreenModel(repository) }

        // Variáveis de estado para guardar o que o utilizador escreve
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(if (taskId == null) "Nova Tarefa" else "Editar Tarefa") },
                    navigationIcon = {
                        Button(onClick = { navigator.pop() }) {
                            Text("Voltar")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    if (title.isNotBlank()) {
                        screenModel.saveTask(title, description) {
                            navigator.pop() // Volta automaticamente para a lista após salvar
                        }
                    }
                }) {
                    Text("Guardar")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título da Tarefa") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição (Opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
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