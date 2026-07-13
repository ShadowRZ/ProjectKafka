package io.github.shadowrz.projectkafka.compose

import androidx.compose.animation.Crossfade
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.result.ResultEffect
import androidx.navigation3.runtime.result.rememberResultEventBus
import androidx.navigation3.runtime.result.rememberResultEventBusNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.compose.di.HanekokoroGraph
import io.github.shadowrz.projectkafka.compose.di.SystemBinding
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Surface
import io.github.shadowrz.projectkafka.designsystem.animation.materialSharedAxisX
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberScreen
import io.github.shadowrz.projectkafka.features.ftue.api.FtueScreen
import io.github.shadowrz.projectkafka.features.ftue.api.FtueState
import io.github.shadowrz.projectkafka.features.home.api.HomeScreen
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileScreen
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.rememberNavigator
import io.github.shadowrz.projectkafka.libraries.architecture.rememberNavigatorNavEntryDecorator
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsCache
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore
import io.github.shadowrz.projectkafka.libraries.resultevents.ResultEvents
import io.github.shadowrz.projectkafka.libraries.systemgraph.SystemGraphCache
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Inject
class KafkaApp(
    private val appCoroutineScope: CoroutineScope,
    private val entryProviders: Set<NavEntryProvider>,
    private val systemGraphCache: SystemGraphCache,
    private val systemsCache: SystemsCache,
    private val systemsStore: SystemsStore,
    private val appPreferencesStore: AppPreferencesStore,
) {
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        showSplashScreen: () -> Unit = {},
    ) {
        val useSystemFont by appPreferencesStore.useSystemFont().collectAsState(false)

        KafkaTheme(useSystemFont = useSystemFont) {
            Surface(modifier = modifier) {
                RootUI(showSplashScreen = showSplashScreen)
            }
        }
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Composable
    @Suppress("detekt:CyclomaticComplexMethod")
    private fun RootUI(
        modifier: Modifier = Modifier,
        showSplashScreen: () -> Unit = {},
    ) {
        var flow by retain {
            mutableStateOf<NavTarget>(NavTarget.SplashScreen)
        }

        LaunchedEffect(Unit) {
            systemsStore.lastSystemID().distinctUntilChanged().collect { systemID ->
                flow =
                    if (systemID != null) {
                        NavTarget.SystemFlow(systemsCache.get(systemID))
                    } else {
                        println("NoSystem")
                        NavTarget.NoSystem
                    }
            }
        }

        Crossfade(
            modifier = modifier,
            targetState = flow,
        ) {
            when (it) {
                NavTarget.SplashScreen -> {}
                NavTarget.NoSystem -> {
                    val navigator =
                        rememberNavigator(
                            configuration = SeralizationModule.CONFIG,
                            WelcomeScreen,
                        )
                    val resuleEventBus = rememberResultEventBus()

                    NavDisplay(
                        backStack = navigator.backStack,
                        modifier = modifier,
                        entryDecorators =
                            listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberRetainedValuesStoreNavEntryDecorator(),
                                rememberResultEventBusNavEntryDecorator(resuleEventBus),
                                rememberNavigatorNavEntryDecorator(navigator),
                            ),
                        entryProvider =
                            entryProvider {
                                entryProviders.forEach { provider ->
                                    with(provider) {
                                        provideEntry()
                                    }
                                }
                            },
                        transitionSpec = { materialSharedAxisX(forward = true) },
                        popTransitionSpec = { materialSharedAxisX(forward = false) },
                        predictivePopTransitionSpec = { materialSharedAxisX(forward = false) },
                    )

                    ResultEffect<ResultEvents>(resultEventBus = resuleEventBus) { ev ->
                        when (ev) {
                            is ResultEvents.SystemCreated -> {
                                appCoroutineScope.launch {
                                    systemsStore.updateSystemLastUsed(
                                        id = ev.id,
                                        lastUsed = Clock.System.now(),
                                    )
                                }
                            }
                            else -> {}
                        }
                    }

                    DisposableEffect(Unit) {
                        showSplashScreen()
                        onDispose {}
                    }
                }
                is NavTarget.SystemFlow -> {
                    val graph =
                        retain(it.system) {
                            systemGraphCache.getOrCreate(it.system)
                        }
                    val entryProviders =
                        retain(graph) {
                            (graph as HanekokoroGraph).entryProviders
                        }
                    val ftueService =
                        retain(graph) {
                            (graph as SystemBinding).ftueService
                        }

                    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
                    val navigator =
                        rememberNavigator(
                            configuration = SeralizationModule.CONFIG,
                            HomeScreen,
                        )
                    val resuleEventBus = rememberResultEventBus()

                    NavDisplay(
                        backStack = navigator.backStack,
                        modifier = modifier,
                        sceneStrategies = listOf(listDetailStrategy),
                        entryDecorators =
                            listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberRetainedValuesStoreNavEntryDecorator(),
                                rememberResultEventBusNavEntryDecorator(resuleEventBus),
                                rememberNavigatorNavEntryDecorator(navigator),
                            ),
                        entryProvider =
                            entryProvider {
                                entryProviders.forEach { provider ->
                                    with(provider) {
                                        provideEntry()
                                    }
                                }
                            },
                        transitionSpec = { materialSharedAxisX(forward = true) },
                        popTransitionSpec = { materialSharedAxisX(forward = false) },
                        predictivePopTransitionSpec = { materialSharedAxisX(forward = false) },
                    )

                    ResultEffect<ResultEvents>(resultEventBus = resuleEventBus) { ev ->
                        when (ev) {
                            is ResultEvents.SystemCreated -> {
                                navigator.pop()
                            }
                            is ResultEvents.MemberDeleted -> {
                                navigator.backStack.remove(MemberProfileScreen(ev.id))
                                navigator.backStack.remove(EditMemberScreen(ev.id))
                            }
                        }
                    }

                    LaunchedEffect(Unit) {
                        ftueService.state.collect { state ->
                            when (state) {
                                FtueState.Unknown -> {
                                    // Nothing to do
                                }

                                FtueState.Incomplete -> {
                                    navigator.navigateTo(FtueScreen)
                                    showSplashScreen()
                                }

                                FtueState.Complete -> {
                                    navigator.navigateTo(HomeScreen)
                                    showSplashScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    sealed interface NavTarget {
        data object SplashScreen : NavTarget

        data object NoSystem : NavTarget

        data class SystemFlow(val system: System) : NavTarget
    }
}
