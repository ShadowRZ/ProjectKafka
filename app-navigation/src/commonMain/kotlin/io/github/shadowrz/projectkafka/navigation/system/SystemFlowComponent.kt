package io.github.shadowrz.projectkafka.navigation.system

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin
import io.github.shadowrz.hanekokoro.framework.runtime.waitForChildAttached
import io.github.shadowrz.projectkafka.features.about.api.AboutEntryPoint
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageEntryPoint
import io.github.shadowrz.projectkafka.features.editmember.api.AddMemberEntryPoint
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberEntryPoint
import io.github.shadowrz.projectkafka.features.ftue.api.FtueEntryPoint
import io.github.shadowrz.projectkafka.features.ftue.api.FtueService
import io.github.shadowrz.projectkafka.features.ftue.api.FtueState
import io.github.shadowrz.projectkafka.features.home.api.HomeEntryPoint
import io.github.shadowrz.projectkafka.features.licenses.api.LicenseEntryPoint
import io.github.shadowrz.projectkafka.features.share.api.ShareData
import io.github.shadowrz.projectkafka.features.share.api.ShareEntryPoint
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.ReadyCallback
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.navigation.intent.ResolvedIntent
import io.github.shadowrz.projectkafka.navigation.maybeReplaceAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@AssistedInject
@ContributesComponent(SystemScope::class)
class SystemFlowComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    coroutineDispatchers: CoroutineDispatchers,
    private val system: System,
    private val systemsStore: SystemsStore,
    private val appCoroutineScope: CoroutineScope,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
    private val membersStore: MembersStore,
    private val ftueService: FtueService,
    private val ftueEntryPoint: FtueEntryPoint,
    private val homeEntryPoint: HomeEntryPoint,
    private val aboutEntryPoint: AboutEntryPoint,
    private val licenseEntryPoint: LicenseEntryPoint,
    private val addMemberEntryPoint: AddMemberEntryPoint,
    private val editMemberEntryPoint: EditMemberEntryPoint,
    private val shareEntryPoint: ShareEntryPoint,
    private val dataManageEntryPoint: DataManageEntryPoint,
    private val switchSystemEntryPoint: SwitchSystemEntryPoint,
    private val createSystemEntryPoint: CreateSystemEntryPoint,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ),
    Resolver<SystemFlowComponent.NavTarget, SystemFlowComponent.Resolved>,
    OnBackCallbackOwner {
    init {
        lifecycle.doOnCreate {
            ftueService.state
                .onEach {
                    when (it) {
                        FtueState.Unknown -> Unit
                        FtueState.Incomplete -> {
                            navigation.maybeReplaceAll(NavTarget.Ftue)
                            readyCallback.ready()
                        }
                        FtueState.Complete -> {
                            navigation.maybeReplaceAll(NavTarget.Home)
                            readyCallback.ready()
                        }
                    }
                }.launchIn(lifecycleScope)
        }
    }

    private val lifecycleScope = coroutineScope(coroutineDispatchers.main)

    private val readyCallback = plugin<ReadyCallback>()

    private val navigation = StackNavigation<NavTarget>()

    val childStack: Value<ChildStack<NavTarget, Resolved>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.Placeholder,
            handleBackButton = true,
            childFactory = ::resolve,
        )

    @OptIn(ExperimentalTime::class)
    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            NavTarget.Placeholder -> Resolved.Placeholder
            NavTarget.Ftue -> {
                Resolved.Ftue(
                    ftueEntryPoint.build(
                        parent = this,
                        context = componentContext,
                    ),
                )
            }
            NavTarget.Home -> {
                readyCallback.ready()

                val callback =
                    object : HomeEntryPoint.Callback {
                        override fun onAbout() {
                            navigation.pushNew(NavTarget.About)
                        }

                        override fun onAddMember() {
                            navigation.pushNew(NavTarget.AddMember)
                        }

                        override fun onEditMember(memberID: MemberID) {
                            navigation.pushNew(NavTarget.EditMember(memberID))
                        }

                        override fun onDataManage() {
                            navigation.pushNew(NavTarget.DataManage)
                        }

                        override fun onSwitchSystem() {
                            navigation.pushNew(NavTarget.SwitchSystem)
                        }
                    }
                Resolved.Home(
                    homeEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        callback = callback,
                    ),
                )
            }

            NavTarget.About -> {
                val callback =
                    object : AboutEntryPoint.Callback {
                        override fun onLicenses() {
                            navigation.pushNew(NavTarget.Licenses)
                        }
                    }
                Resolved.About(
                    aboutEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        callback = callback,
                    ),
                )
            }

            NavTarget.Licenses ->
                Resolved.Licenses(
                    licenseEntryPoint.build(
                        parent = this,
                        context = componentContext,
                    ),
                )

            NavTarget.AddMember ->
                Resolved.AddMember(
                    addMemberEntryPoint.build(
                        parent = this,
                        context = componentContext,
                    ),
                )
            is NavTarget.Share ->
                Resolved.Share(
                    shareEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        shareData = navTarget.shareData,
                    ),
                )

            is NavTarget.EditMember -> {
                val callback = object : EditMemberEntryPoint.Callback {
                    override fun onDeleteMember() {
                        systemCoroutineScope.launch {
                            onDeleteMember(navTarget.memberID)
                        }
                    }
                }
                Resolved.EditMember(
                    editMemberEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        memberID = navTarget.memberID,
                        callback = callback,
                    ),
                )
            }

            NavTarget.DataManage ->
                Resolved.DataManage(
                    dataManageEntryPoint.build(
                        parent = this,
                        context = componentContext,
                    ),
                )

            NavTarget.SwitchSystem -> {
                val callback = object : SwitchSystemEntryPoint.Callback {
                    override fun onCreateSystem() {
                        navigation.pushNew(NavTarget.CreateSystem)
                    }

                    override fun onSwitchSystem(id: SystemID) {
                        if (id == system.id) {
                            navigation.pop()
                        } else {
                            appCoroutineScope.launch {
                                systemsStore.updateSystemLastUsed(id, Clock.System.now())
                            }
                        }
                    }
                }
                Resolved.DataManage(
                    switchSystemEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        callback = callback,
                    ),
                )
            }

            NavTarget.CreateSystem -> {
                val callback = object : CreateSystemEntryPoint.Callback {
                    override fun onFinished(id: SystemID) {
                        navigation.pop()
                    }
                }

                Resolved.DataManage(
                    createSystemEntryPoint.build(
                        parent = this,
                        context = componentContext,
                        callback = callback,
                    ),
                )
            }
        }

    override fun onBack() {
        navigation.pop()
    }

    suspend fun onIncomingShare(incomingShare: ResolvedIntent.IncomingShare) {
        childStack.waitForChildAttached { navTarget ->
            navTarget == NavTarget.Home
        }
        navigation.pushNew(NavTarget.Share(incomingShare.shareData))
    }

    private suspend fun onDeleteMember(memberID: MemberID) {
        val resolved = childStack.waitForChildAttached { navTarget ->
            navTarget == NavTarget.Home
        }
        ((resolved as Resolved.Home).component as HomeEntryPoint.Actions).dismissMemberPane {
            systemCoroutineScope.launch {
                membersStore.deleteMember(memberID)
            }
        }
    }

    @Serializable
    sealed interface NavTarget {
        @Serializable
        data object Placeholder : NavTarget

        @Serializable
        data object Ftue : NavTarget

        @Serializable
        data object Home : NavTarget

        @Serializable
        data object About : NavTarget

        @Serializable
        data object AddMember : NavTarget

        @Serializable
        data object Licenses : NavTarget

        @Serializable
        data class Share(
            val shareData: ShareData,
        ) : NavTarget

        @Serializable
        data class EditMember(
            val memberID: MemberID,
        ) : NavTarget

        @Serializable
        data object DataManage : NavTarget

        @Serializable
        data object SwitchSystem : NavTarget

        @Serializable
        data object CreateSystem : NavTarget
    }

    sealed interface Resolved {
        data object Placeholder : Resolved

        data class Ftue(
            val component: Component,
        ) : Resolved

        data class Home(
            val component: Component,
        ) : Resolved

        data class About(
            val component: Component,
        ) : Resolved

        data class AddMember(
            val component: Component,
        ) : Resolved

        data class Licenses(
            val component: Component,
        ) : Resolved

        data class Share(
            val component: Component,
        ) : Resolved

        data class EditMember(
            val component: Component,
        ) : Resolved

        data class DataManage(
            val component: Component,
        ) : Resolved

        data class SwitchSystem(
            val component: Component,
        ) : Resolved

        data class CreateSystem(
            val component: Component,
        ) : Resolved
    }
}
