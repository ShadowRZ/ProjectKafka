package io.github.shadowrz.projectkafka.libraries.components.preview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun previewSheetState() =
    SheetState(
        skipPartiallyExpanded = true,
        initialValue = SheetValue.Expanded,
        positionalThreshold = { 1f },
        velocityThreshold = { 1f },
    )
