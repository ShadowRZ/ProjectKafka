package io.github.shadowrz.projectkafka.features.datamanage.impl

import io.github.vinceglb.filekit.PlatformFile

interface ZipPackager {
    suspend fun packageZip(output: PlatformFile)
}
