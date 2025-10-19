package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.ChatBubbleOutline: ImageVector
    get() {
        if (_ChatBubbleOutline != null) {
            return _ChatBubbleOutline!!
        }
        _ChatBubbleOutline = ImageVector.Builder(
            name = "ChatBubbleOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(2f, 22f)
                lineTo(2f, 4f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(4f, 2f)
                horizontalLineToRelative(16f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(22f, 4f)
                verticalLineToRelative(12f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(20f, 18f)
                lineTo(6f, 18f)
                close()
                moveTo(5.15f, 16f)
                lineTo(20f, 16f)
                lineTo(20f, 4f)
                lineTo(4f, 4f)
                verticalLineToRelative(13.125f)
                close()
                moveTo(4f, 16f)
                lineTo(4f, 4f)
                close()
            }
        }.build()

        return _ChatBubbleOutline!!
    }

@Suppress("ObjectPropertyName")
private var _ChatBubbleOutline: ImageVector? = null
