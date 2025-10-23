package io.github.shadowrz.projectkafka.libraries.mediapickers.impl

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.eygraber.uri.Uri
import com.eygraber.uri.toAndroidUri
import com.eygraber.uri.toKmpUri
import android.net.Uri as AndroidUri

internal object ConvertContracts {
    internal class FromAndroidUri<O>(
        private val delegate: ActivityResultContract<AndroidUri, O>,
    ) : ActivityResultContract<Uri, O>() {
        override fun createIntent(
            context: Context,
            input: Uri,
        ): Intent = delegate.createIntent(context, input.toAndroidUri())

        override fun parseResult(
            resultCode: Int,
            intent: Intent?,
        ): O = delegate.parseResult(resultCode, intent)

        override fun getSynchronousResult(
            context: Context,
            input: Uri,
        ): SynchronousResult<O>? = delegate.getSynchronousResult(context, input.toAndroidUri())
    }

    internal class FromNullableAndroidUri<O>(
        private val delegate: ActivityResultContract<AndroidUri?, O>,
    ) : ActivityResultContract<Uri?, O>() {
        override fun createIntent(
            context: Context,
            input: Uri?,
        ): Intent = delegate.createIntent(context, input?.toAndroidUri())

        override fun parseResult(
            resultCode: Int,
            intent: Intent?,
        ): O = delegate.parseResult(resultCode, intent)

        override fun getSynchronousResult(
            context: Context,
            input: Uri?,
        ): SynchronousResult<O>? = delegate.getSynchronousResult(context, input?.toAndroidUri())
    }

    internal class ToKmpUri<I>(
        private val delegate: ActivityResultContract<I, AndroidUri>,
    ) : ActivityResultContract<I, Uri>() {
        override fun createIntent(
            context: Context,
            input: I,
        ): Intent = delegate.createIntent(context, input)

        override fun parseResult(
            resultCode: Int,
            intent: Intent?,
        ): Uri = delegate.parseResult(resultCode, intent).toKmpUri()

        override fun getSynchronousResult(
            context: Context,
            input: I,
        ): SynchronousResult<Uri>? =
            delegate.getSynchronousResult(context, input)?.run {
                SynchronousResult(value.toKmpUri())
            }
    }

    internal class ToNullableKmpUri<I>(
        private val delegate: ActivityResultContract<I, AndroidUri?>,
    ) : ActivityResultContract<I, Uri?>() {
        override fun createIntent(
            context: Context,
            input: I,
        ): Intent = delegate.createIntent(context, input)

        override fun parseResult(
            resultCode: Int,
            intent: Intent?,
        ): Uri? = delegate.parseResult(resultCode, intent)?.toKmpUri()

        override fun getSynchronousResult(
            context: Context,
            input: I,
        ): SynchronousResult<Uri?>? =
            delegate.getSynchronousResult(context, input)?.run {
                SynchronousResult(value?.toKmpUri())
            }
    }
}
