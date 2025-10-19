package io.github.shadowrz.projectkafka.libraries.emojipicker

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.emoji2.emojipicker.EmojiViewItem
import androidx.emoji2.emojipicker.RecentEmojiProvider

/**
 * Jetpack Compose UI adaption of [EmojiPickerView].
 */
@Composable
fun EmojiPicker(
    onPickEmoji: (EmojiViewItem) -> Unit,
    modifier: Modifier = Modifier,
    emojiGridColumns: Int? = null,
    emojiGridRows: Float? = null,
    recentEmojiProvider: RecentEmojiProvider? = null,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            EmojiPickerView(context).apply {
                emojiGridRows?.let {
                    this.emojiGridRows = it
                }
                emojiGridColumns?.let {
                    this.emojiGridColumns = it
                }
                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                setOnEmojiPickedListener {
                    onPickEmoji(it)
                }
                recentEmojiProvider?.let {
                    setRecentEmojiProvider(it)
                }
            }
        },
        update = { view ->
            emojiGridRows?.let {
                view.emojiGridRows = it
            }
            emojiGridColumns?.let {
                view.emojiGridColumns = it
            }
        },
    )
}

@Preview
@Composable
internal fun EmojiPickerPreview() {
    EmojiPicker(
        onPickEmoji = {},
    )
}
