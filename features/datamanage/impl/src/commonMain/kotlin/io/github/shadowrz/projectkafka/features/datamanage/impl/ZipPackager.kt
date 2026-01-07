package io.github.shadowrz.projectkafka.features.datamanage.impl

import com.eygraber.uri.Uri

interface ZipPackager {
    suspend fun packageZip(output: Uri)
}
