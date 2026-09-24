package project.to.doapp.arthur

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    App(databaseDriverFactory = DatabaseDriverFactory())
}