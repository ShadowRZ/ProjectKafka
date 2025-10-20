package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Globe: ImageVector
    get() {
        if (_Globe != null) {
            return _Globe!!
        }
        _Globe = ImageVector.Builder(
            name = "Globe",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 22f)
                quadToRelative(-2.075f, 0f, -3.9f, -0.788f)
                reflectiveQuadToRelative(-3.175f, -2.137f)
                reflectiveQuadTo(2.788f, 15.9f)
                reflectiveQuadTo(2f, 12f)
                reflectiveQuadToRelative(0.788f, -3.9f)
                reflectiveQuadToRelative(2.137f, -3.175f)
                reflectiveQuadTo(8.1f, 2.788f)
                reflectiveQuadTo(12f, 2f)
                reflectiveQuadToRelative(3.9f, 0.788f)
                reflectiveQuadToRelative(3.175f, 2.137f)
                reflectiveQuadTo(21.213f, 8.1f)
                reflectiveQuadTo(22f, 12f)
                reflectiveQuadToRelative(-0.788f, 3.9f)
                reflectiveQuadToRelative(-2.137f, 3.175f)
                reflectiveQuadToRelative(-3.175f, 2.138f)
                reflectiveQuadTo(12f, 22f)
                moveToRelative(0f, -2f)
                quadToRelative(3.35f, 0f, 5.675f, -2.325f)
                reflectiveQuadTo(20f, 12f)
                quadToRelative(0f, -0.175f, -0.012f, -0.363f)
                reflectiveQuadToRelative(-0.013f, -0.312f)
                quadToRelative(-0.125f, 0.725f, -0.675f, 1.2f)
                reflectiveQuadTo(18f, 13f)
                horizontalLineToRelative(-2f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(14f, 11f)
                verticalLineToRelative(-1f)
                horizontalLineToRelative(-4f)
                verticalLineTo(8f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(12f, 6f)
                horizontalLineToRelative(1f)
                quadToRelative(0f, -0.575f, 0.313f, -1.012f)
                reflectiveQuadToRelative(0.762f, -0.713f)
                quadToRelative(-0.5f, -0.125f, -1.012f, -0.2f)
                reflectiveQuadTo(12f, 4f)
                quadTo(8.65f, 4f, 6.325f, 6.325f)
                reflectiveQuadTo(4f, 12f)
                horizontalLineToRelative(5f)
                quadToRelative(1.65f, 0f, 2.825f, 1.175f)
                reflectiveQuadTo(13f, 16f)
                verticalLineToRelative(1f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(2.75f)
                quadToRelative(0.5f, 0.125f, 0.988f, 0.188f)
                reflectiveQuadTo(12f, 20f)
            }
        }.build()

        return _Globe!!
    }

@Suppress("ObjectPropertyName")
private var _Globe: ImageVector? = null
