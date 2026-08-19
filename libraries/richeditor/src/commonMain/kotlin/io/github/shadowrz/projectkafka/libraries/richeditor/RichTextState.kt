package io.github.shadowrz.projectkafka.libraries.richeditor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import com.mohamedrejeb.richeditor.model.RichTextState

@Composable
fun rememberRichTextState(
    initialHtml: String,
    historyLimit: Int = 100,
    coalesceWindowMs: Long = 500L,
): RichTextState {
    return rememberSaveable(saver = RichTextState.Saver) {
        RichTextState(historyLimit = historyLimit, coalesceWindowMs = coalesceWindowMs).apply {
            setHtml(html = initialHtml)
        }
    }
}

fun RichTextState(html: String): RichTextState =
    RichTextState(
            historyLimit = 0,
            coalesceWindowMs = 0L,
        )
        .apply { setHtml(html) }
