package io.github.shadowrz.projectkafka.features.home.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.router.panels.Panels
import com.arkivanov.decompose.router.panels.PanelsNavigation
import com.arkivanov.decompose.router.panels.activateDetails
import com.arkivanov.decompose.router.panels.childPanels
import com.arkivanov.decompose.router.panels.dismissDetails
import com.arkivanov.decompose.router.panels.setMode
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.features.home.api.HomeEntryPoint
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsComponent
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewComponent
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsComponent
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineComponent
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.architecture.createComponent
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@OptIn(
    ExperimentalDecomposeApi::class,
    ExperimentalSerializationApi::class,
)
@AssistedInject
class HomeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    internal val system: System,
    private val memberProfileEntryPoint: MemberProfileEntryPoint,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    Resolver<HomeComponent.MainNavTarget, HomeComponent.MainResolved>,
    OnBackCallbackOwner,
    HomeEntryPoint.Actions {
    private val panelsNavigation = PanelsNavigation<Unit, DetailNavTarget, Nothing>()
    private val slotNavigation = SlotNavigation<MainNavTarget>()
    private val callback = plugin<HomeEntryPoint.Callback>()

    internal val panels: Value<ChildPanels<Unit, Unit, DetailNavTarget, DetailResolved, Nothing, Nothing>> =
        childPanels(
            source = panelsNavigation,
            serializers = Unit.serializer() to DetailNavTarget.serializer(),
            initialPanels = { Panels(main = Unit) },
            handleBackButton = true,
            mainFactory = { _, _ -> },
            detailsFactory = ::resolve,
        )

    internal val slot: Value<ChildSlot<MainNavTarget, MainResolved>> =
        childSlot(
            source = slotNavigation,
            serializer = MainNavTarget.serializer(),
            initialConfiguration = { MainNavTarget.Overview },
            handleBackButton = false,
            childFactory = ::resolve,
        )

    override fun resolve(
        navTarget: MainNavTarget,
        componentContext: ComponentContext,
    ): MainResolved =
        when (navTarget) {
            MainNavTarget.Overview -> {
                val callback =
                    object : OverviewComponent.Callback {
                        override fun onAddMember() {
                            callback.onAddMember()
                        }
                    }
                MainResolved.Overview(
                    createComponent<OverviewComponent>(
                        context = componentContext,
                        plugins = listOf(callback),
                    ),
                )
            }
            MainNavTarget.Timeline ->
                MainResolved.Timeline(
                    createComponent<TimelineComponent>(
                        context = componentContext,
                    ),
                )
            MainNavTarget.Chats ->
                MainResolved.Chats(
                    createComponent<ChatsComponent>(
                        context = componentContext,
                    ),
                )
            MainNavTarget.Polls ->
                MainResolved.Polls(
                    createComponent<PollsComponent>(
                        context = componentContext,
                    ),
                )
        }

    private fun resolve(
        navTarget: DetailNavTarget,
        componentContext: ComponentContext,
    ): DetailResolved =
        when (navTarget) {
            is DetailNavTarget.MemberProfile -> {
                val memberID = navTarget.memberID
                val callback = object : MemberProfileEntryPoint.Callback {
                    override fun onEditMember() {
                        callback.onEditMember(memberID)
                    }
                }

                DetailResolved.MemberProfile(
                    memberProfileEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        memberID = memberID,
                        callback = callback,
                    ),
                )
            }
        }

    internal fun onNewNavTarget(navTarget: MainNavTarget) {
        slotNavigation.activate(navTarget)
    }

    internal fun setMode(mode: ChildPanelsMode) {
        panelsNavigation.setMode(mode)
    }

    internal fun openMember(memberID: MemberID) {
        panelsNavigation.activateDetails(DetailNavTarget.MemberProfile(memberID))
    }

    internal fun onAbout() {
        callback.onAbout()
    }

    override fun onBack() {
        if (panels.value.mode == ChildPanelsMode.SINGLE) {
            panelsNavigation.dismissDetails()
        }
    }

    override fun dismissMemberPane(onComplete: () -> Unit) {
        panelsNavigation.dismissDetails { _, _ -> onComplete() }
    }

    internal fun onDataManage() {
        callback.onDataManage()
    }

    internal fun onSwitchSystem() {
        callback.onSwitchSystem()
    }

    @Serializable
    sealed interface MainNavTarget {
        @Serializable
        data object Overview : MainNavTarget

        @Serializable
        data object Timeline : MainNavTarget

        @Serializable
        data object Chats : MainNavTarget

        @Serializable
        data object Polls : MainNavTarget
    }

    sealed interface MainResolved {
        data class Overview(
            val component: OverviewComponent,
        ) : MainResolved

        data class Timeline(
            val component: TimelineComponent,
        ) : MainResolved

        data class Chats(
            val component: ChatsComponent,
        ) : MainResolved

        data class Polls(
            val component: PollsComponent,
        ) : MainResolved
    }

    @Serializable
    sealed interface DetailNavTarget {
        @Serializable
        data class MemberProfile(
            val memberID: MemberID,
        ) : DetailNavTarget
    }

    sealed interface DetailResolved {
        data class MemberProfile(
            val component: Component,
        ) : DetailResolved
    }

    @ContributesIntoMap(
        SystemScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(HomeComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<HomeComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): HomeComponent
    }
}
