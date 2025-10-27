package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import io.github.shadowrz.projectkafka.libraries.architecture.Presenter

expect class AddDetailsPresenter : Presenter<AddDetailsState> {
    fun interface Factory {
        fun create(
            systemName: String,
            callback: AddDetailsComponent.Callback,
            croppers: AddDetailsComponent.Croppers,
        ): AddDetailsPresenter
    }
}
