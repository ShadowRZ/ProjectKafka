package io.github.shadowrz.projectkafka.features.fronterindicator.api

import androidx.compose.runtime.Stable

@Stable
interface FronterIndicatorLauncher {
    fun launch()

    object NoOp : FronterIndicatorLauncher {
        override fun launch() {}
    }
}
