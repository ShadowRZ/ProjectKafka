package io.github.shadowrz.projectkafka.features.home.impl.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesBinding(SystemScope::class)
actual class TimelinePresenter : Presenter<TimelineState> {
    @Composable
    override fun present(): TimelineState {
        var timelineType by rememberSaveable { mutableStateOf(TimelineType.Activity) }
        return TimelineState(
            timelineType = timelineType,
        ) {
            when (it) {
                is TimelineEvents.ChangeTimelineType -> {
                    timelineType = it.timelineType
                }
            }
        }
    }
}
