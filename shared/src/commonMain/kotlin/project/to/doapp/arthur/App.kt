package project.to.doapp.arthur

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.CompositionLocalProvider
import todoapp.shared.generated.resources.Res
import todoapp.shared.generated.resources.compose_multiplatform
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

// Criamos um ponto de acesso global para o nosso Repositório na UI
val LocalTaskRepository = staticCompositionLocalOf<TaskRepository> {
    error("TaskRepository não foi providenciado!")
}

@Composable
fun App(databaseDriverFactory: DatabaseDriverFactory) {
    // Instancia o repositório apenas uma vez
    val repository = remember { TaskRepository(databaseDriverFactory) }

    // Torna o repositório disponível para todos os ecrãs de forma invisível
    CompositionLocalProvider(LocalTaskRepository provides repository) {
        MaterialTheme {
            Navigator(TaskListScreen()) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}