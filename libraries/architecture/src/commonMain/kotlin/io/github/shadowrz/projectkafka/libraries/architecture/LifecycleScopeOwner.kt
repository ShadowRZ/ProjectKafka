package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope

interface LifecycleScopeOwner : LifecycleOwner {
    val lifecycleScope: CoroutineScope
}
