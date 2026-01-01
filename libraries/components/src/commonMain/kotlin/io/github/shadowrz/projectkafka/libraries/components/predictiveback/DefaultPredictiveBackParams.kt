package io.github.shadowrz.projectkafka.libraries.components.predictiveback

import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.materialPredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackHandler
import io.github.shadowrz.projectkafka.libraries.components.PLATFORM_SUPPORTS_PREDICTIVE_BACK

fun defaultPredictiveBackParams(
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

fun defaultPredictiveBackParams(
    backHandler: BackHandler,
    onBack: () -> Unit,
) = defaultPredictiveBackParams(
    enabled = PLATFORM_SUPPORTS_PREDICTIVE_BACK,
    backHandler = backHandler,
    onBack = onBack,
)
