package io.github.shadowrz.projectkafka.libraries.mediapickers.impl

import androidx.activity.result.contract.ActivityResultContract
import io.github.shadowrz.projectkafka.libraries.mediapickers.api.PickerLauncher
import android.net.Uri as AndroidUri

internal fun <I, O> PickerLauncher<I, O>.toDefaultOnlyPicker(): PickerLauncher<Nothing, O> = DefaultOnlyPickerLauncher(this)

internal fun <I> ActivityResultContract<I, AndroidUri>.toKmpUriContract() = ConvertContracts.ToKmpUri(this)

internal fun <I> ActivityResultContract<I, AndroidUri?>.toKmpUriContract() = ConvertContracts.ToNullableKmpUri(this)

internal fun <O> ActivityResultContract<AndroidUri, O>.toKmpUriContract() = ConvertContracts.FromAndroidUri(this)

internal fun <O> ActivityResultContract<AndroidUri?, O>.toKmpUriContract() = ConvertContracts.FromNullableAndroidUri(this)
