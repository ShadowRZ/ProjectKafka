package io.github.shadowrz.projectkafka.features.datamanage.impl

import com.eygraber.uri.Uri
import okio.Path

interface ZipValidator {
    suspend fun unpackAndValidateZip(input: Uri): Result

    sealed interface Result {
        data class Ok(
            val unpacked: Path,
        ) : Result

        data object Invalid : Result
    }
}
