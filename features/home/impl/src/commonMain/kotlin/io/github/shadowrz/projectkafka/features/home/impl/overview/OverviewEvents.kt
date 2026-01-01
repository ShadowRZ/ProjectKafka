package io.github.shadowrz.projectkafka.features.home.impl.overview

sealed interface OverviewEvents {
    data class ToggleFabMenu(
        val expanded: Boolean,
    ) : OverviewEvents

    data class ChangeOverviewSection(
        val overviewSection: OverviewSection,
    ) : OverviewEvents

    data object AddMember : OverviewEvents

    data object LaunchFronterIndicator : OverviewEvents
}
