package io.github.shadowrz.projectkafka.features.editmember.impl

import com.attafitamim.krop.core.crop.ImageCropper
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter

expect class AddMemberPresenter : Presenter<AddMemberState> {
    fun interface Factory {
        fun create(
            callback: AddMemberComponent.Callback,
            imageCropper: ImageCropper,
        ): AddMemberPresenter
    }
}
