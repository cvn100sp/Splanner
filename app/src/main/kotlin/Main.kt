package com.windows.splanner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

val Sandboxed_storage = File(System.getProperty("user.home"), "Splanner")
val userdata=File(Sandboxed_storage,"tasks.db")
var currentActivity by mutableStateOf(0)

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Цель. Код будущего"
    ) {
        if(Sandboxed_storage.exists()){
            currentActivity=1
        }
        when(currentActivity){
            0 -> StartupActivity()
            1 -> MainActivity()
        }
    }
}