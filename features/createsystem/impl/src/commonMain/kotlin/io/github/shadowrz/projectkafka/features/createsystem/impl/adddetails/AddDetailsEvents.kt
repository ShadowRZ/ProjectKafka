package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

sealed interface AddDetailsEvents {
    data object CreateSystem : AddDetailsEvents
}
