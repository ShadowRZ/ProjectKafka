package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Rotate90DegreesCwOutline: ImageVector
    get() {
        if (_Rotate90DegreesCwOutline != null) {
            return _Rotate90DegreesCwOutline!!
        }
        _Rotate90DegreesCwOutline = ImageVector.Builder(
            name = "Rotate90DegreesCwOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11f, 22f)
                quadToRelative(-1.875f, 0f, -3.512f, -0.712f)
                reflectiveQuadToRelative(-2.85f, -1.925f)
                reflectiveQuadToRelative(-1.926f, -2.85f)
                reflectiveQuadTo(2f, 13f)
                quadToRelative(0f, -3.75f, 2.625f, -6.375f)
                reflectiveQuadTo(11f, 4f)
                horizontalLineToRelative(0.15f)
                lineTo(9.6f, 2.45f)
                lineTo(11f, 1f)
                lineToRelative(4f, 4f)
                lineToRelative(-4f, 4f)
                lineToRelative(-1.4f, -1.45f)
                lineTo(11.15f, 6f)
                lineTo(11f, 6f)
                quadTo(8.075f, 6f, 6.038f, 8.038f)
                reflectiveQuadTo(4f, 13f)
                reflectiveQuadToRelative(2.038f, 4.963f)
                reflectiveQuadTo(11f, 20f)
                quadToRelative(0.875f, 0f, 1.725f, -0.213f)
                reflectiveQuadToRelative(1.625f, -0.637f)
                lineToRelative(1.45f, 1.45f)
                quadToRelative(-1.075f, 0.7f, -2.3f, 1.05f)
                reflectiveQuadTo(11f, 22f)
                moveToRelative(6f, -3f)
                lineToRelative(-6f, -6f)
                lineToRelative(6f, -6f)
                lineToRelative(6f, 6f)
                close()
                moveTo(17f, 16.15f)
                lineTo(20.15f, 13f)
                lineTo(17f, 9.85f)
                lineTo(13.85f, 13f)
                close()
                moveTo(17f, 13f)
            }
        }.build()

        return _Rotate90DegreesCwOutline!!
    }

@Suppress("ObjectPropertyName")
private var _Rotate90DegreesCwOutline: ImageVector? = null
