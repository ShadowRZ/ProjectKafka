package io.github.shadowrz.projectkafka.features.home.impl.overview

import io.github.shadowrz.projectkafka.libraries.architecture.Presenter

expect class OverviewPresenter : Presenter<OverviewState> {
    fun interface Factory {
        fun create(callback: OverviewComponent.Callback): OverviewPresenter
    }
}
