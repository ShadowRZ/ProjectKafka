package io.github.shadowrz.projectkafka.compose.intent

import io.github.shadowrz.projectkafka.features.share.api.ShareData

sealed interface ResolvedIntent {
    data class IncomingShare(
        val shareData: ShareData,
    ) : ResolvedIntent
}
