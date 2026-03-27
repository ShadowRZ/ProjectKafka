package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@NonRestartableComposable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    androidx.compose.material3.LoadingIndicator(modifier = modifier)
}
