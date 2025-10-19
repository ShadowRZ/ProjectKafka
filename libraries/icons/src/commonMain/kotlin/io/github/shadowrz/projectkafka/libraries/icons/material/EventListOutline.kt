package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.EventListOutline: ImageVector
    get() {
        if (_EventListOutline != null) {
            return _EventListOutline!!
        }
        _EventListOutline = ImageVector.Builder(
            name = "EventListOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16f, 21f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(14f, 19f)
                verticalLineToRelative(-4f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(16f, 13f)
                horizontalLineToRelative(4f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(22f, 15f)
                verticalLineToRelative(4f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(20f, 21f)
                close()
                moveTo(16f, 19f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(-4f)
                horizontalLineToRelative(-4f)
                close()
                moveTo(2f, 18f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(9f)
                verticalLineToRelative(2f)
                close()
                moveTo(16f, 11f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(14f, 9f)
                lineTo(14f, 5f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(16f, 3f)
                horizontalLineToRelative(4f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(22f, 5f)
                verticalLineToRelative(4f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(20f, 11f)
                close()
                moveTo(16f, 9f)
                horizontalLineToRelative(4f)
                lineTo(20f, 5f)
                horizontalLineToRelative(-4f)
                close()
                moveTo(2f, 8f)
                lineTo(2f, 6f)
                horizontalLineToRelative(9f)
                verticalLineToRelative(2f)
                close()
                moveTo(18f, 7f)
            }
        }.build()

        return _EventListOutline!!
    }

@Suppress("ObjectPropertyName")
private var _EventListOutline: ImageVector? = null
