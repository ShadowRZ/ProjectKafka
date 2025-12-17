package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.MembersState

@Stable
actual data class OverviewState(
    val fabMenuExpanded: Boolean,
    val overviewSection: OverviewSection,
    val membersState: MembersState,
    val eventSink: (OverviewEvents) -> Unit,
) : HanekokoroState
