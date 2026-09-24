package project.to.doapp.arthur

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

// --- TELA 1: LISTA DE TAREFAS ---
class TaskListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val repository = LocalTaskRepository.current
        val screenModel = rememberScreenModel { TaskListScreenModel(repository) }

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
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    // Aqui tornamos o cartão clicável para abrir a edição
                                    .clickable { navigator.push(TaskDetailScreen(taskId = task.id)) }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = task.completed,
                                        onCheckedChange = { screenModel.toggleTaskCompletion(task) }
                                    )
                                    Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                                        Text(task.title, style = MaterialTheme.typography.titleMedium)
                                        if (task.description != null) {
                                            Text(task.description, style = MaterialTheme.typography.bodyMedium)
                                        }
                                    }
                                    IconButton(onClick = { screenModel.deleteTask(task.id) }) {
                                        Text("🗑️") // Mantemos o seu emoji!
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

// --- TELA 2: CRIAR / EDITAR TAREFA ---
class TaskDetailScreen(val taskId: Long? = null) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val repository = LocalTaskRepository.current
        val screenModel = rememberScreenModel { TaskDetailScreenModel(repository) }

        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }

        // Carrega os dados se for uma edição
        LaunchedEffect(taskId) {
            if (taskId != null) {
                val task = screenModel.getTask(taskId)
                if (task != null) {
                    title = task.title
                    description = task.description ?: ""
                }
            }
        }

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
                        screenModel.saveTask(taskId, title, description) {
                            navigator.pop()
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