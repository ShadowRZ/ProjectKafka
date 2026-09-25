package io.github.shadowrz.projectkafka.features.share.api

import kotlinx.serialization.Serializable

@Serializable
sealed interface ShareData {
    @Serializable data class Text(val text: String) : ShareData

    @Serializable data class Files(val uris: List<String>) : ShareData
}
