package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.SystemsPresenter

@Inject
class SwitchSystemPresenter(private val systemsPresenter: SystemsPresenter) : Presenter<SwitchSystemState> {

    @Composable
    override fun present(): SwitchSystemState {
        val systems = systemsPresenter.present()

        return SwitchSystemState(systems = systems.systems)
    }
}
