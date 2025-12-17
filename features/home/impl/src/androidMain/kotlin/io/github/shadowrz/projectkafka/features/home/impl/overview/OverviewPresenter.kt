package io.github.shadowrz.projectkafka.features.home.impl.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.features.fronterindicator.api.FronterIndicatorEntryPoint
import io.github.shadowrz.projectkafka.features.home.impl.overview.members.MembersState
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesBinding(SystemScope::class)
actual class OverviewPresenter(
    @Assisted private val callback: OverviewComponent.Callback,
    private val membersPresenter: Presenter<MembersState>,
    private val fronterIndicatorEntryPoint: FronterIndicatorEntryPoint,
) : Presenter<OverviewState> {
    @Composable
    override fun present(): OverviewState {
        var fabMenuExpanded by rememberSaveable {
            mutableStateOf(false)
        }
        var overviewSection by rememberSaveable {
            mutableStateOf(OverviewSection.Members)
        }
        val membersState = membersPresenter.present()
        val fronterIndicatorLauncher = fronterIndicatorEntryPoint.rememberFronterIndicatorLauncher()

        return OverviewState(
            fabMenuExpanded = fabMenuExpanded,
            overviewSection = overviewSection,
            membersState = membersState,
        ) {
            when (it) {
                is OverviewEvents.ChangeOverviewSection -> overviewSection = it.overviewSection
                is OverviewEvents.ToggleFabMenu -> fabMenuExpanded = it.expanded
                OverviewEvents.AddMember -> callback.onAddMember()
                OverviewEvents.LaunchFronterIndicator -> fronterIndicatorLauncher.launch()
            }
        }
    }

    @AssistedFactory
    actual fun interface Factory {
        actual fun create(callback: OverviewComponent.Callback): OverviewPresenter
    }
}
