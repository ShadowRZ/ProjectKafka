package io.github.shadowrz.projectkafka.libraries.richeditor

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.ImageLoader
import com.mohamedrejeb.richeditor.model.LocalImageLoader
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.TokenClickHandler
import com.mohamedrejeb.richeditor.model.TokenHoverHandler
import com.mohamedrejeb.richeditor.ui.material3.RichText

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun RichText(
    state: RichTextState,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    imageLoader: ImageLoader = LocalImageLoader.current,
    onTokenClick: TokenClickHandler? = null,
    onTokenHover: TokenHoverHandler? = null,
) {
    RichText(
        modifier = modifier,
        state = state,
        style = style,
        textAlign = textAlign,
        lineHeight = lineHeight,
        fontSize = fontSize,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        fontWeight = fontWeight,
        color = color,
        fontFamily = fontFamily,
        minLines = minLines,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        textDecoration = textDecoration,
        inlineContent = inlineContent,
        imageLoader = imageLoader,
        onTokenClick = onTokenClick,
        onTokenHover = onTokenHover,
    )
}
