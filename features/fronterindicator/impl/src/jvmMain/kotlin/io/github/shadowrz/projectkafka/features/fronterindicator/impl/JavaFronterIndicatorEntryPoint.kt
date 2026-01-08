package io.github.shadowrz.projectkafka.features.fronterindicator.impl

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.features.fronterindicator.api.FronterIndicatorEntryPoint
import io.github.shadowrz.projectkafka.features.fronterindicator.api.FronterIndicatorLauncher

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
class JavaFronterIndicatorEntryPoint : FronterIndicatorEntryPoint {
    @Composable
    override fun rememberFronterIndicatorLauncher(): FronterIndicatorLauncher = FronterIndicatorLauncher.NoOp
}
