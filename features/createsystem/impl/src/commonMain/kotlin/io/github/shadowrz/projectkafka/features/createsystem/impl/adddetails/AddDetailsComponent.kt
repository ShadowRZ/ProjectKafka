package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

class AddDetailsComponent(presenterFactory: AddDetailsPresenter.Factory) {
    interface Callback : Plugin {
        fun onFinish(id: SystemID)
    }
}
