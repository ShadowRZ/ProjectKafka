package io.github.shadowrz.projectkafka

import androidx.compose.material3.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Project Kafka",
        ) {
            Text("WIP!\nEventually this will host desktop version of Project Kafka")
        }
    }
