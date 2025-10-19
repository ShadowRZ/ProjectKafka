package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.DateRangeOutline: ImageVector
    get() {
        if (_DateRangeOutline != null) {
            return _DateRangeOutline!!
        }
        _DateRangeOutline = ImageVector.Builder(
            name = "DateRangeOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(8f, 14f)
                quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
                reflectiveQuadTo(7f, 13f)
                reflectiveQuadToRelative(0.288f, -0.712f)
                reflectiveQuadTo(8f, 12f)
                reflectiveQuadToRelative(0.713f, 0.288f)
                reflectiveQuadTo(9f, 13f)
                reflectiveQuadToRelative(-0.288f, 0.713f)
                reflectiveQuadTo(8f, 14f)
                moveToRelative(4f, 0f)
                quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
                reflectiveQuadTo(11f, 13f)
                reflectiveQuadToRelative(0.288f, -0.712f)
                reflectiveQuadTo(12f, 12f)
                reflectiveQuadToRelative(0.713f, 0.288f)
                reflectiveQuadTo(13f, 13f)
                reflectiveQuadToRelative(-0.288f, 0.713f)
                reflectiveQuadTo(12f, 14f)
                moveToRelative(4f, 0f)
                quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
                reflectiveQuadTo(15f, 13f)
                reflectiveQuadToRelative(0.288f, -0.712f)
                reflectiveQuadTo(16f, 12f)
                reflectiveQuadToRelative(0.713f, 0.288f)
                reflectiveQuadTo(17f, 13f)
                reflectiveQuadToRelative(-0.288f, 0.713f)
                reflectiveQuadTo(16f, 14f)
                moveTo(5f, 22f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(3f, 20f)
                lineTo(3f, 6f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(5f, 4f)
                horizontalLineToRelative(1f)
                lineTo(6f, 2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(8f)
                lineTo(16f, 2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(1f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(21f, 6f)
                verticalLineToRelative(14f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(19f, 22f)
                close()
                moveTo(5f, 20f)
                horizontalLineToRelative(14f)
                lineTo(19f, 10f)
                lineTo(5f, 10f)
                close()
                moveTo(5f, 8f)
                horizontalLineToRelative(14f)
                lineTo(19f, 6f)
                lineTo(5f, 6f)
                close()
                moveTo(5f, 8f)
                lineTo(5f, 6f)
                close()
            }
        }.build()

        return _DateRangeOutline!!
    }

@Suppress("ObjectPropertyName")
private var _DateRangeOutline: ImageVector? = null
