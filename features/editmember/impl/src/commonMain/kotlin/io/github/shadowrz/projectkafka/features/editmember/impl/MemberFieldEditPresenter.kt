package io.github.shadowrz.projectkafka.features.editmember.impl

import com.attafitamim.krop.core.crop.ImageCropper
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import kotlinx.coroutines.flow.StateFlow

expect class MemberFieldEditPresenter : Presenter<MemberFieldEditState> {
    fun interface Factory {
        fun create(
            initialState: MemberFieldEditState.FieldState,
            imageCropper: ImageCropper,
            callback: MemberFieldEditCallback,
        ): MemberFieldEditPresenter
    }
}
