package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.ui.platform.UriHandler

actual fun UriHandler.openUrlInCustomTab(url: String) = openUri(url)
