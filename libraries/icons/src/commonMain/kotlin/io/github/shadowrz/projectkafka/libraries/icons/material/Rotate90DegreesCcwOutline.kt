package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Rotate90DegreesCcwOutline: ImageVector
    get() {
        if (_Rotate90DegreesCcwOutline != null) {
            return _Rotate90DegreesCcwOutline!!
        }
        _Rotate90DegreesCcwOutline = ImageVector.Builder(
            name = "Rotate90DegreesCcwOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(13f, 22f)
                quadToRelative(-1.275f, 0f, -2.5f, -0.35f)
                reflectiveQuadTo(8.2f, 20.6f)
                lineToRelative(1.45f, -1.45f)
                quadToRelative(0.775f, 0.425f, 1.625f, 0.638f)
                reflectiveQuadTo(13f, 20f)
                quadToRelative(2.925f, 0f, 4.962f, -2.038f)
                reflectiveQuadTo(20f, 13f)
                reflectiveQuadToRelative(-2.037f, -4.962f)
                reflectiveQuadTo(13f, 6f)
                horizontalLineToRelative(-0.15f)
                lineToRelative(1.55f, 1.55f)
                lineTo(13f, 9f)
                lineTo(9f, 5f)
                lineToRelative(4f, -4f)
                lineToRelative(1.4f, 1.45f)
                lineTo(12.85f, 4f)
                lineTo(13f, 4f)
                quadToRelative(3.75f, 0f, 6.375f, 2.625f)
                reflectiveQuadTo(22f, 13f)
                quadToRelative(0f, 1.875f, -0.712f, 3.513f)
                reflectiveQuadToRelative(-1.925f, 2.85f)
                reflectiveQuadToRelative(-2.85f, 1.925f)
                reflectiveQuadTo(13f, 22f)
                moveToRelative(-6f, -3f)
                lineToRelative(-6f, -6f)
                lineToRelative(6f, -6f)
                lineToRelative(6f, 6f)
                close()
                moveTo(7f, 16.15f)
                lineTo(10.15f, 13f)
                lineTo(7f, 9.85f)
                lineTo(3.85f, 13f)
                close()
                moveTo(7f, 13f)
            }
        }.build()

        return _Rotate90DegreesCcwOutline!!
    }

@Suppress("ObjectPropertyName")
private var _Rotate90DegreesCcwOutline: ImageVector? = null
