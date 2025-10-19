package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.runtime.Stable
import io.github.shadowrz.projectkafka.libraries.profile.api.SelectImageState

@Stable
data class AddDetailsState(
    val avatarState: SelectImageState,
    val coverState: SelectImageState,
    val systemName: String,
    val loading: Boolean,
    val eventSink: (AddDetailsEvents) -> Unit,
)
