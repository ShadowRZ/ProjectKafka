package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.CameraOutline: ImageVector
    get() {
        if (_CameraOutline != null) {
            return _CameraOutline!!
        }
        _CameraOutline = ImageVector.Builder(
            name = "CameraOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.4f, 9f)
                horizontalLineToRelative(8f)
                quadToRelative(-0.675f, -1.725f, -2.062f, -2.963f)
                reflectiveQuadTo(14.15f, 4.3f)
                close()
                moveTo(9.1f, 11f)
                lineToRelative(4f, -6.9f)
                quadToRelative(-0.275f, -0.05f, -0.55f, -0.075f)
                reflectiveQuadTo(12f, 4f)
                quadToRelative(-1.65f, 0f, -3.075f, 0.625f)
                reflectiveQuadTo(6.4f, 6.3f)
                close()
                moveTo(4.25f, 14f)
                lineTo(9.7f, 14f)
                lineToRelative(-4f, -6.9f)
                quadToRelative(-0.8f, 1.025f, -1.25f, 2.263f)
                reflectiveQuadTo(4f, 12f)
                quadToRelative(0f, 0.525f, 0.063f, 1.013f)
                reflectiveQuadTo(4.25f, 14f)
                moveToRelative(5.6f, 5.7f)
                lineToRelative(2.7f, -4.7f)
                lineTo(4.6f, 15f)
                quadToRelative(0.675f, 1.725f, 2.063f, 2.963f)
                reflectiveQuadTo(9.85f, 19.7f)
                moveTo(12f, 20f)
                quadToRelative(1.65f, 0f, 3.075f, -0.625f)
                reflectiveQuadTo(17.6f, 17.7f)
                lineTo(14.9f, 13f)
                lineToRelative(-4f, 6.9f)
                quadToRelative(0.275f, 0.05f, 0.538f, 0.075f)
                reflectiveQuadTo(12f, 20f)
                moveToRelative(6.3f, -3.1f)
                quadToRelative(0.8f, -1.025f, 1.25f, -2.262f)
                reflectiveQuadTo(20f, 12f)
                quadToRelative(0f, -0.525f, -0.062f, -1.012f)
                reflectiveQuadTo(19.75f, 10f)
                lineTo(14.3f, 10f)
                close()
                moveTo(12f, 22f)
                quadToRelative(-2.05f, 0f, -3.875f, -0.788f)
                reflectiveQuadToRelative(-3.187f, -2.15f)
                reflectiveQuadToRelative(-2.15f, -3.187f)
                reflectiveQuadTo(2f, 12f)
                quadToRelative(0f, -2.075f, 0.788f, -3.887f)
                reflectiveQuadToRelative(2.15f, -3.175f)
                reflectiveQuadToRelative(3.187f, -2.15f)
                reflectiveQuadTo(12f, 2f)
                quadToRelative(2.075f, 0f, 3.888f, 0.788f)
                reflectiveQuadToRelative(3.174f, 2.15f)
                reflectiveQuadToRelative(2.15f, 3.175f)
                reflectiveQuadTo(22f, 12f)
                quadToRelative(0f, 2.05f, -0.788f, 3.875f)
                reflectiveQuadToRelative(-2.15f, 3.188f)
                reflectiveQuadToRelative(-3.175f, 2.15f)
                reflectiveQuadTo(12f, 22f)
            }
        }.build()

        return _CameraOutline!!
    }

@Suppress("ObjectPropertyName")
private var _CameraOutline: ImageVector? = null
