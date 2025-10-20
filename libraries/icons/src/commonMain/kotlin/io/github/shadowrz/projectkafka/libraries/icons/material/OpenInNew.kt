package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.OpenInNew: ImageVector
    get() {
        if (_OpenInNew != null) {
            return _OpenInNew!!
        }
        _OpenInNew = ImageVector.Builder(
            name = "OpenInNew",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5f, 21f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(3f, 19f)
                lineTo(3f, 5f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(5f, 3f)
                horizontalLineToRelative(7f)
                verticalLineToRelative(2f)
                lineTo(5f, 5f)
                verticalLineToRelative(14f)
                horizontalLineToRelative(14f)
                verticalLineToRelative(-7f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(7f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(19f, 21f)
                close()
                moveTo(9.7f, 15.7f)
                lineToRelative(-1.4f, -1.4f)
                lineTo(17.6f, 5f)
                lineTo(14f, 5f)
                lineTo(14f, 3f)
                horizontalLineToRelative(7f)
                verticalLineToRelative(7f)
                horizontalLineToRelative(-2f)
                lineTo(19f, 6.4f)
                close()
            }
        }.build()

        return _OpenInNew!!
    }

@Suppress("ObjectPropertyName")
private var _OpenInNew: ImageVector? = null
