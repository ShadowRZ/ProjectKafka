package io.github.shadowrz.projectkafka.libraries.core.saver

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import com.eygraber.uri.Uri

object UriSaver : Saver<Uri, String> {
    override fun restore(value: String): Uri? = Uri.parseOrNull(value)

    override fun SaverScope.save(value: Uri): String? = value.toString()
}
