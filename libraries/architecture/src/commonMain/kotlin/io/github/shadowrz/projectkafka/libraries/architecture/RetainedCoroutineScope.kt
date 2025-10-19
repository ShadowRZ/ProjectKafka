package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

@Suppress("detekt:FunctionName")
fun RetainedCoroutineScope(context: CoroutineContext = Dispatchers.Main + SupervisorJob()) =
    RetainedCoroutineScope(CoroutineScope(context = context))

class RetainedCoroutineScope internal constructor(
    private val scope: CoroutineScope,
) : InstanceKeeper.Instance,
    CoroutineScope by scope {
    override fun onDestroy() {
        this.cancel()
    }
}
