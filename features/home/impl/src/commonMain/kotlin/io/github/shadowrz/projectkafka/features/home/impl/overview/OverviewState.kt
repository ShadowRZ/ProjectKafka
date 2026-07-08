package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState

@Stable
data class OverviewState(
    val fabMenuExpanded: Boolean,
    val overviewSection: OverviewSection,
    val membersState: MembersState,
    val eventSink: (OverviewEvents) -> Unit,
) : HanekokoroState
