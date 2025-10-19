package io.github.shadowrz.projectkafka.features.fronterindicator.api

import androidx.compose.runtime.Composable

interface FronterIndicatorEntryPoint {
    @Composable
    fun rememberFronterIndicatorLauncher(): FronterIndicatorLauncher
}
