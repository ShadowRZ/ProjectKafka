package io.github.shadowrz.projectkafka.features.ftue.impl.notification

import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin

class NotificationComponent {
    interface Callback : Plugin {
        fun onDone()
    }
}
