package io.github.shadowrz.projectkafka.compose

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.metadata
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.result.ResultEffect
import androidx.navigation3.runtime.result.rememberResultEventBus
import androidx.navigation3.runtime.result.rememberResultEventBusNavEntryDecorator
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.compose.di.HanekokoroGraph
import io.github.shadowrz.projectkafka.compose.di.SystemBinding
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Surface
import io.github.shadowrz.projectkafka.designsystem.animation.materialSharedAxisX
import io.github.shadowrz.projectkafka.designsystem.animation.rememberSlideDistance
import io.github.shadowrz.projectkafka.designsystem.navigation3.rememberListDetailSceneStrategy
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberScreen
import io.github.shadowrz.projectkafka.features.ftue.api.FtueScreen
import io.github.shadowrz.projectkafka.features.ftue.api.FtueState
import io.github.shadowrz.projectkafka.features.home.api.HomeScreen
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileScreen
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.rememberNavigator
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
    @OptIn(ExperimentalSharedTransitionApi::class)
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

    @Composable
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
                NavTarget.NoSystem -> NoSystemUI(showSplashScreen = showSplashScreen)
                is NavTarget.SystemFlow -> SystemUI(system = it.system, showSplashScreen = showSplashScreen)
            }
        }
    }

    @Composable
    private fun NoSystemUI(
        modifier: Modifier = Modifier,
        showSplashScreen: () -> Unit = {},
    ) {
        val showSplashScreen by rememberUpdatedState(showSplashScreen)
        val navigator =
            rememberNavigator(
                configuration = SeralizationModule.CONFIG,
                WelcomeScreen,
            )
        val resultEventBus = rememberResultEventBus()
        val slideDistance = rememberSlideDistance()

        SharedTransitionScope {
            NavDisplay(
                backStack = navigator.backStack,
                modifier = modifier.then(it),
                onBack = navigator::pop,
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberRetainedValuesStoreNavEntryDecorator(),
                        rememberResultEventBusNavEntryDecorator(resultEventBus),
                    ),
                entryProvider =
                    entryProvider {
                        entryProviders.forEach { provider ->
                            with(provider) {
                                provideEntry(
                                    navigator = navigator,
                                    sharedTransitionScope = this@SharedTransitionScope,
                                )
                            }
                        }
                    },
                transitionSpec = { materialSharedAxisX(forward = true, slideDistance = slideDistance) },
                popTransitionSpec = { materialSharedAxisX(forward = false, slideDistance = slideDistance) },
                predictivePopTransitionSpec = { materialSharedAxisX(forward = false, slideDistance = slideDistance) },
            )
        }

        ResultEffect<ResultEvents>(resultEventBus = resultEventBus) { ev ->
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

    @Suppress("detekt:CyclomaticComplexMethod")
    @Composable
    private fun SystemUI(
        system: System,
        modifier: Modifier = Modifier,
        showSplashScreen: () -> Unit = {},
    ) {
        val showSplashScreen by rememberUpdatedState(showSplashScreen)
        val graph =
            retain(system) {
                systemGraphCache.getOrCreate(system)
            }
        val entryProviders =
            retain(graph) {
                (graph as HanekokoroGraph).entryProviders
            }
        val ftueService =
            retain(graph) {
                (graph as SystemBinding).ftueService
            }
        val systemCoroutineScope =
            retain(graph) {
                (graph as SystemBinding).coroutineScope
            }

        val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
        val navigator =
            rememberNavigator(
                configuration = SeralizationModule.CONFIG,
                LoadingScreen,
            )
        val resultEventBus = rememberResultEventBus()
        val slideDistance = rememberSlideDistance()

        SharedTransitionScope {
            NavDisplay(
                backStack = navigator.backStack,
                modifier = modifier.then(it),
                onBack = navigator::pop,
                sceneStrategies = listOf(listDetailStrategy),
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberRetainedValuesStoreNavEntryDecorator(),
                        rememberResultEventBusNavEntryDecorator(resultEventBus),
                        remember {
                            NavEntryDecorator { entry ->
                                val animatedContentScope = LocalNavAnimatedContentScope.current
                                DisposableEffect(animatedContentScope.transition.isRunning) {
                                    if (navigator.backStack[0] != LoadingScreen && !animatedContentScope.transition.isRunning) {
                                        showSplashScreen()
                                    }
                                    onDispose {}
                                }
                                entry.Content()
                            }
                        },
                    ),
                entryProvider =
                    entryProvider {
                        entryProviders.forEach { provider ->
                            with(provider) {
                                provideEntry(
                                    navigator = navigator,
                                    sharedTransitionScope = this@SharedTransitionScope,
                                )
                            }
                        }
                        entry<LoadingScreen>(
                            metadata =
                                metadata {
                                    put(NavDisplay.TransitionKey) {
                                        fadeIn() togetherWith fadeOut()
                                    }
                                    put(NavDisplay.PopTransitionKey) {
                                        fadeIn() togetherWith fadeOut()
                                    }
                                    put(NavDisplay.PredictivePopTransitionKey) {
                                        fadeIn() togetherWith fadeOut()
                                    }
                                }
                        ) {
                            Surface(modifier = Modifier.fillMaxSize()) {}
                        }
                    },
                transitionSpec = { materialSharedAxisX(forward = true, slideDistance = slideDistance) },
                popTransitionSpec = { materialSharedAxisX(forward = false, slideDistance = slideDistance) },
                predictivePopTransitionSpec = { materialSharedAxisX(forward = false, slideDistance = slideDistance) },
            )
        }

        ResultEffect<ResultEvents>(resultEventBus = resultEventBus) { ev ->
            when (ev) {
                is ResultEvents.SystemCreated -> {
                    navigator.pop()
                }
                is ResultEvents.MemberDeleted -> {
                    navigator.backStack.remove(MemberProfileScreen(ev.id))
                    navigator.backStack.remove(EditMemberScreen(ev.id))
                    systemCoroutineScope.launch {
                        val membersStore = (graph as SystemBinding).membersStore
                        membersStore.deleteMember(ev.id)
                    }
                }
                is ResultEvents.SwitchSystem -> {
                    appCoroutineScope.launch {
                        systemsStore.updateSystemLastUsed(
                            ev.id,
                            Clock.System.now(),
                        )
                    }
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
                        navigator.backStack.apply {
                            if (!contains(FtueScreen)) {
                                clear()
                                add(FtueScreen)
                            }
                        }
                    }

                    FtueState.Complete -> {
                        navigator.backStack.apply {
                            if (!contains(HomeScreen)) {
                                clear()
                                add(HomeScreen)
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
