package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.cropper.api.CropperState

@Stable
data class AddDetailsState(
    val avatar: String,
    val cover: String,
    val avatarCropper: CropperState,
    val coverCropper: CropperState,
    val systemName: String,
    val loading: Boolean,
    val showAvatarSheet: Boolean,
    val showCoverSheet: Boolean,
    val showCamera: Boolean,
    val eventSink: (AddDetailsEvents) -> Unit,
) : HanekokoroState
