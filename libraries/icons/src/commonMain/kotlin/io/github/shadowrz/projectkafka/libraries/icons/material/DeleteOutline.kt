package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.DeleteOutline: ImageVector
    get() {
        if (_DeleteOutline != null) {
            return _DeleteOutline!!
        }
        _DeleteOutline = ImageVector.Builder(
            name = "DeleteOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7f, 21f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(5f, 19f)
                lineTo(5f, 6f)
                lineTo(4f, 6f)
                lineTo(4f, 4f)
                horizontalLineToRelative(5f)
                lineTo(9f, 3f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(1f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-1f)
                verticalLineToRelative(13f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(17f, 21f)
                close()
                moveTo(17f, 6f)
                lineTo(7f, 6f)
                verticalLineToRelative(13f)
                horizontalLineToRelative(10f)
                close()
                moveTo(9f, 17f)
                horizontalLineToRelative(2f)
                lineTo(11f, 8f)
                lineTo(9f, 8f)
                close()
                moveTo(13f, 17f)
                horizontalLineToRelative(2f)
                lineTo(15f, 8f)
                horizontalLineToRelative(-2f)
                close()
                moveTo(7f, 6f)
                verticalLineToRelative(13f)
                close()
            }
        }.build()

        return _DeleteOutline!!
    }

@Suppress("ObjectPropertyName")
private var _DeleteOutline: ImageVector? = null
