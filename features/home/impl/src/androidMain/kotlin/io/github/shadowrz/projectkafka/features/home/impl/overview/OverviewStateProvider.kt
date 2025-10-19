package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.MembersState
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.aMembers
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.aMembersState

internal class OverviewStateProvider : PreviewParameterProvider<OverviewState> {
    override val values: Sequence<OverviewState>
        get() =
            sequenceOf(
                aDashboardState(),
                aDashboardState(fabMenuExpanded = true),
                aDashboardState(
                    membersState = aMembersState(),
                ),
                aDashboardState(
                    membersState = aMembersState(members = emptyList()),
                ),
                aDashboardState(
                    membersState = aMembersState(members = aMembers()),
                ),
//                aDashboardState(
//                    frontersState = aFrontersState(),
//                    dashboardSection = OverviewSection.Fronters,
//                ),
//                aDashboardState(
//                    frontersState = aFrontersState(fronters = emptyList()),
//                    dashboardSection = OverviewSection.Fronters,
//                ),
//                aDashboardState(
//                    frontersState = aFrontersState(fronters = aMembers()),
//                    dashboardSection = OverviewSection.Fronters,
//                ),
                aDashboardState(
                    overviewSection = OverviewSection.Tools,
                ),
            )
}

private fun aDashboardState(
    membersState: MembersState = aMembersState(),
    // frontersState: FrontersState = aFrontersState(),
    fabMenuExpanded: Boolean = false,
    overviewSection: OverviewSection = OverviewSection.Members,
): OverviewState =
    OverviewState(
        fabMenuExpanded = fabMenuExpanded,
        overviewSection = overviewSection,
        membersState = membersState,
//        frontersState = frontersState,
    ) {
    }
