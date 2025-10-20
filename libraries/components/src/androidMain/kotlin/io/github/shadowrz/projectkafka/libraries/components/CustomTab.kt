package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.ui.platform.UriHandler
import io.github.shadowrz.projectkafka.libraries.androidutils.CustomTabUriHandler

actual fun UriHandler.openUrlInCustomTab(url: String) {
    if (this is CustomTabUriHandler) {
        openUriInCustomTab(url)
    } else {
        openUri(url)
    }
}
