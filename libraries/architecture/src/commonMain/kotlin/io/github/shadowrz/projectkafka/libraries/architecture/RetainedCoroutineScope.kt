package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

private class RetainedCoroutineScope(
    private val scope: CoroutineScope,
) : InstanceKeeper.Instance,
    CoroutineScope by scope {
    override fun onDestroy() {
        this.cancel()
    }
}

fun InstanceKeeperOwner.retainedCoroutineScope(context: CoroutineContext = Dispatchers.Main + SupervisorJob()): CoroutineScope =
    instanceKeeper.getOrCreate {
        RetainedCoroutineScope(CoroutineScope(context = context))
    }
