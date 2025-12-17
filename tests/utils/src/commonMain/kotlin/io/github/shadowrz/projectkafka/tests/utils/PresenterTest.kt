package io.github.shadowrz.projectkafka.tests.utils

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import kotlin.time.Duration

suspend fun <S : HanekokoroState> Presenter<S>.test(
    timeout: Duration? = null,
    name: String? = null,
    validate: suspend TurbineTestContext<S>.() -> Unit,
) = moleculeFlow(RecompositionMode.Immediate) {
    present()
}.test(
    timeout = timeout,
    name = name,
    validate = validate,
)
