package io.github.shadowrz.projectkafka.features.fronterindicator.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.features.fronterindicator.api.FronterIndicatorEntryPoint
import io.github.shadowrz.projectkafka.features.fronterindicator.api.FronterIndicatorLauncher

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
class AndroidFronterIndicatorEntryPoint : FronterIndicatorEntryPoint {
    @Composable
    override fun rememberFronterIndicatorLauncher(): FronterIndicatorLauncher {
        val context = LocalContext.current

        return remember {
            AndroidFronterIndicatorLauncher(context)
        }
    }
}
