package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Groups: ImageVector
    get() {
        if (_Groups != null) {
            return _Groups!!
        }
        _Groups = ImageVector.Builder(
            name = "Groups",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(0f, 18f)
                verticalLineToRelative(-1.575f)
                quadToRelative(0f, -1.075f, 1.1f, -1.75f)
                reflectiveQuadTo(4f, 14f)
                quadToRelative(0.325f, 0f, 0.625f, 0.013f)
                reflectiveQuadToRelative(0.575f, 0.062f)
                quadToRelative(-0.35f, 0.525f, -0.525f, 1.1f)
                reflectiveQuadToRelative(-0.175f, 1.2f)
                lineTo(4.5f, 18f)
                close()
                moveTo(6f, 18f)
                verticalLineToRelative(-1.625f)
                quadToRelative(0f, -0.8f, 0.438f, -1.463f)
                reflectiveQuadToRelative(1.237f, -1.162f)
                reflectiveQuadTo(9.588f, 13f)
                reflectiveQuadTo(12f, 12.75f)
                quadToRelative(1.325f, 0f, 2.438f, 0.25f)
                reflectiveQuadToRelative(1.912f, 0.75f)
                reflectiveQuadToRelative(1.225f, 1.163f)
                reflectiveQuadToRelative(0.425f, 1.462f)
                lineTo(18f, 18f)
                close()
                moveTo(19.5f, 18f)
                verticalLineToRelative(-1.625f)
                quadToRelative(0f, -0.65f, -0.162f, -1.225f)
                reflectiveQuadToRelative(-0.488f, -1.075f)
                quadToRelative(0.275f, -0.05f, 0.563f, -0.062f)
                reflectiveQuadTo(20f, 14f)
                quadToRelative(1.8f, 0f, 2.9f, 0.663f)
                reflectiveQuadToRelative(1.1f, 1.762f)
                lineTo(24f, 18f)
                close()
                moveTo(4f, 13f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(2f, 11f)
                quadToRelative(0f, -0.85f, 0.588f, -1.425f)
                reflectiveQuadTo(4f, 9f)
                quadToRelative(0.85f, 0f, 1.425f, 0.575f)
                reflectiveQuadTo(6f, 11f)
                quadToRelative(0f, 0.825f, -0.575f, 1.413f)
                reflectiveQuadTo(4f, 13f)
                moveToRelative(16f, 0f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(18f, 11f)
                quadToRelative(0f, -0.85f, 0.588f, -1.425f)
                reflectiveQuadTo(20f, 9f)
                quadToRelative(0.85f, 0f, 1.425f, 0.575f)
                reflectiveQuadTo(22f, 11f)
                quadToRelative(0f, 0.825f, -0.575f, 1.413f)
                reflectiveQuadTo(20f, 13f)
                moveToRelative(-8f, -1f)
                quadToRelative(-1.25f, 0f, -2.125f, -0.875f)
                reflectiveQuadTo(9f, 9f)
                quadToRelative(0f, -1.275f, 0.875f, -2.137f)
                reflectiveQuadTo(12f, 6f)
                quadToRelative(1.275f, 0f, 2.138f, 0.863f)
                reflectiveQuadTo(15f, 9f)
                quadToRelative(0f, 1.25f, -0.862f, 2.125f)
                reflectiveQuadTo(12f, 12f)
            }
        }.build()

        return _Groups!!
    }

@Suppress("ObjectPropertyName")
private var _Groups: ImageVector? = null
