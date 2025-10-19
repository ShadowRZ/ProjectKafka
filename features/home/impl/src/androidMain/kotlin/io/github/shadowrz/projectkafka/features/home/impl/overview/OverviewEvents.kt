package io.github.shadowrz.projectkafka.features.home.impl.overview

actual sealed interface OverviewEvents {
    actual data class ToggleFabMenu(
        val expanded: Boolean,
    ) : OverviewEvents

    actual data class ChangeOverviewSection(
        val overviewSection: OverviewSection,
    ) : OverviewEvents

    actual data object AddMember : OverviewEvents

    actual data object LaunchFronterIndicator : OverviewEvents
}
