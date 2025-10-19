package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Construction: ImageVector
    get() {
        if (_Construction != null) {
            return _Construction!!
        }
        _Construction = ImageVector.Builder(
            name = "Construction",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(18.9f, 21f)
                lineToRelative(-5.475f, -5.475f)
                lineToRelative(2.1f, -2.1f)
                lineTo(21f, 18.9f)
                close()
                moveTo(5.1f, 21f)
                lineTo(3f, 18.9f)
                lineTo(9.9f, 12f)
                lineToRelative(-1.7f, -1.7f)
                lineToRelative(-0.7f, 0.7f)
                lineToRelative(-1.275f, -1.275f)
                verticalLineToRelative(2.05f)
                lineToRelative(-0.7f, 0.7f)
                lineTo(2.5f, 9.45f)
                lineToRelative(0.7f, -0.7f)
                horizontalLineToRelative(2.05f)
                lineTo(4f, 7.5f)
                lineToRelative(3.55f, -3.55f)
                quadToRelative(0.5f, -0.5f, 1.075f, -0.725f)
                reflectiveQuadTo(9.8f, 3f)
                reflectiveQuadToRelative(1.175f, 0.225f)
                reflectiveQuadToRelative(1.075f, 0.725f)
                lineToRelative(-2.3f, 2.3f)
                lineTo(11f, 7.5f)
                lineToRelative(-0.7f, 0.7f)
                lineTo(12f, 9.9f)
                lineToRelative(2.25f, -2.25f)
                quadToRelative(-0.1f, -0.275f, -0.162f, -0.575f)
                reflectiveQuadToRelative(-0.063f, -0.6f)
                quadToRelative(0f, -1.475f, 1.013f, -2.488f)
                reflectiveQuadToRelative(2.487f, -1.012f)
                quadToRelative(0.375f, 0f, 0.713f, 0.075f)
                reflectiveQuadToRelative(0.687f, 0.225f)
                lineTo(16.45f, 5.75f)
                lineToRelative(1.8f, 1.8f)
                lineToRelative(2.475f, -2.475f)
                quadToRelative(0.175f, 0.35f, 0.238f, 0.687f)
                reflectiveQuadToRelative(0.062f, 0.713f)
                quadToRelative(0f, 1.475f, -1.012f, 2.488f)
                reflectiveQuadToRelative(-2.488f, 1.012f)
                quadToRelative(-0.3f, 0f, -0.6f, -0.05f)
                reflectiveQuadToRelative(-0.575f, -0.175f)
                close()
            }
        }.build()

        return _Construction!!
    }

@Suppress("ObjectPropertyName")
private var _Construction: ImageVector? = null
