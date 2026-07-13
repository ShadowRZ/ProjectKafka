package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

interface AddDetailsCallback {
    fun onFinish(id: SystemID)
}
