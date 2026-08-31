package io.github.shadowrz.projectkafka.tests.utils

import androidx.compose.runtime.withCompositionLocals
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.navigationevent.testing.TestNavigationEventDispatcherOwner
import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import kotlin.time.Duration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <S : HanekokoroState> Presenter<S>.test(
    timeout: Duration? = null,
    name: String? = null,
    coroutineDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    lifecycleOwner: LifecycleOwner = TestLifecycleOwner(coroutineDispatcher = coroutineDispatcher),
    navigationEventDispatcherOwner: TestNavigationEventDispatcherOwner = TestNavigationEventDispatcherOwner(),
    validate: suspend TurbineTestContext<S>.() -> Unit,
) =
    moleculeFlow(RecompositionMode.Immediate) {
            withCompositionLocals(
                LocalNavigationEventDispatcherOwner provides navigationEventDispatcherOwner,
                LocalLifecycleOwner provides lifecycleOwner,
            ) {
                present()
            }
        }
        .test(
            timeout = timeout,
            name = name,
            validate = validate,
        )
