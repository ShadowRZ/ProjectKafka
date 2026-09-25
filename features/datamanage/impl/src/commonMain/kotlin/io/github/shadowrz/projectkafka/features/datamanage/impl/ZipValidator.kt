package io.github.shadowrz.projectkafka.features.datamanage.impl

import io.github.vinceglb.filekit.PlatformFile
import okio.Path

interface ZipValidator {
    suspend fun unpackAndValidateZip(file: PlatformFile): Result

    sealed interface Result {
        data class Ok(val unpacked: Path) : Result

        data object Invalid : Result
    }
}
