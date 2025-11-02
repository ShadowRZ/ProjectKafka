package io.github.shadowrz.projectkafka.features.home.impl.overview

import io.github.shadowrz.hanekokoro.framework.runtime.Presenter

expect class OverviewPresenter : Presenter<OverviewState> {
    fun interface Factory {
        fun create(callback: OverviewComponent.Callback): OverviewPresenter
    }
}
