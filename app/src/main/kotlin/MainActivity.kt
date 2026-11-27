package com.fuse.splanner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

val Sandboxed_storage = File(System.getProperty("user.home"), "com.fuse.splanner")
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Splanner"
    ) {
        if(!Sandboxed_storage.exists()){
            StartupActivity()
        }
    }
}
