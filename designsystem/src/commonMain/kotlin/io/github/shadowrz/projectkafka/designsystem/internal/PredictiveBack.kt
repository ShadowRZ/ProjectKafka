package io.github.shadowrz.projectkafka.designsystem.internal

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.materialPredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackHandler

internal expect val PLATFORM_SUPPORTS_PREDICTIVE_BACK: Boolean

@OptIn(ExperimentalDecomposeApi::class)
internal fun defaultPredictiveBackParams(
    enabled: Boolean = true,
    backHandler: BackHandler,
    onBack: () -> Unit,
): PredictiveBackParams? =
    if (enabled) {
        PredictiveBackParams(
            backHandler = backHandler,
            onBack = onBack,
            animatable = ::materialPredictiveBackAnimatable,
        )
    } else {
        null
    }

@OptIn(ExperimentalDecomposeApi::class)
internal fun defaultPredictiveBackParams(
    backHandler: BackHandler,
    onBack: () -> Unit,
) = defaultPredictiveBackParams(
    enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
    backHandler = backHandler,
    onBack = onBack,
)
