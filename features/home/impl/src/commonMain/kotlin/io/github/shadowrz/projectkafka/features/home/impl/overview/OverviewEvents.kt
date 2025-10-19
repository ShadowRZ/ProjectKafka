package io.github.shadowrz.projectkafka.features.home.impl.overview

expect sealed interface OverviewEvents {
    class ToggleFabMenu : OverviewEvents

    class ChangeOverviewSection : OverviewEvents

    data object AddMember : OverviewEvents

    data object LaunchFronterIndicator : OverviewEvents
}
