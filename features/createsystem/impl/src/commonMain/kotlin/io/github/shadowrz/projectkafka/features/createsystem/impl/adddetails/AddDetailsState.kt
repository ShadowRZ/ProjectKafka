package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.profile.api.CropperState

@Stable
data class AddDetailsState(
    val avatarState: CropperState,
    val coverState: CropperState,
    val systemName: String,
    val loading: Boolean,
    val eventSink: (AddDetailsEvents) -> Unit,
) : HanekokoroState
