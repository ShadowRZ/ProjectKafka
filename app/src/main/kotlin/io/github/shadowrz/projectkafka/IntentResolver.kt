package io.github.shadowrz.projectkafka

import android.content.Intent
import android.net.Uri
import androidx.core.content.IntentCompat
import co.touchlab.kermit.Logger
import com.eygraber.uri.toKmpUri
import io.github.shadowrz.projectkafka.features.share.api.ShareData
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.core.mimetypes.MimeTypes
import io.github.shadowrz.projectkafka.navigation.intent.ResolvedIntent

object IntentResolver {
    fun resolve(intent: Intent): ResolvedIntent? {
        if (intent.isLauncher()) return null

        return when (intent.action) {
            Intent.ACTION_SEND,
            Intent.ACTION_SEND_MULTIPLE,
            -> {
                intent.toShareData()?.let { ResolvedIntent.IncomingShare(it) }
            }

            else -> {
                Logger
                    .withTag(LoggerTag.IntentResolver.value)
                    .w { "Unknown Intent: $intent" }
                null
            }
        }
    }
}

private fun Intent.isLauncher(): Boolean =
    (
        action == Intent.ACTION_MAIN &&
            categories?.contains(Intent.CATEGORY_LAUNCHER) == true
    )

private fun Intent.toShareData(): ShareData? =
    when (action) {
        Intent.ACTION_SEND -> {
            when (type) {
                MimeTypes.PlainText -> {
                    getStringExtra(Intent.EXTRA_TEXT)?.let { ShareData.Text(it) }
                        ?: IntentCompat
                            .getParcelableExtra(
                                this,
                                Intent.EXTRA_STREAM,
                                Uri::class.java,
                            )?. let {
                                ShareData.Files(
                                    listOf(
                                        it.toKmpUri(),
                                    ),
                                )
                            }
                }

                else -> {
                    IntentCompat
                        .getParcelableExtra(
                            this,
                            Intent.EXTRA_STREAM,
                            Uri::class.java,
                        )?. let {
                            ShareData.Files(
                                listOf(
                                    it.toKmpUri(),
                                ),
                            )
                        }
                }
            }
        }

        Intent.ACTION_SEND_MULTIPLE -> {
            IntentCompat
                .getParcelableArrayListExtra(
                    this,
                    Intent.EXTRA_STREAM,
                    Uri::class.java,
                )?. let {
                    ShareData.Files(
                        it.map { uri -> uri.toKmpUri() },
                    )
                }
        }

        else -> {
            null
        }
    }
