package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

/**
 * Hides the keyboard when disposed.
 */
@Composable
fun DisposeHideKeyboardEffect() {
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(Unit) {
        onDispose {
            keyboardController?.hide()
        }
    }
}
