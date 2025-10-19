package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.AccountCircle: ImageVector
    get() {
        if (_AccountCircle != null) {
            return _AccountCircle!!
        }
        _AccountCircle = ImageVector.Builder(
            name = "AccountCircle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5.85f, 17.1f)
                quadToRelative(1.275f, -0.975f, 2.85f, -1.537f)
                reflectiveQuadTo(12f, 15f)
                reflectiveQuadToRelative(3.3f, 0.563f)
                reflectiveQuadToRelative(2.85f, 1.537f)
                quadToRelative(0.875f, -1.025f, 1.363f, -2.325f)
                reflectiveQuadTo(20f, 12f)
                quadToRelative(0f, -3.325f, -2.337f, -5.663f)
                reflectiveQuadTo(12f, 4f)
                reflectiveQuadTo(6.337f, 6.338f)
                reflectiveQuadTo(4f, 12f)
                quadToRelative(0f, 1.475f, 0.488f, 2.775f)
                reflectiveQuadTo(5.85f, 17.1f)
                moveTo(12f, 13f)
                quadToRelative(-1.475f, 0f, -2.488f, -1.012f)
                reflectiveQuadTo(8.5f, 9.5f)
                reflectiveQuadToRelative(1.013f, -2.488f)
                reflectiveQuadTo(12f, 6f)
                reflectiveQuadToRelative(2.488f, 1.013f)
                reflectiveQuadTo(15.5f, 9.5f)
                reflectiveQuadToRelative(-1.012f, 2.488f)
                reflectiveQuadTo(12f, 13f)
                moveToRelative(0f, 9f)
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
            }
        }.build()

        return _AccountCircle!!
    }

@Suppress("ObjectPropertyName")
private var _AccountCircle: ImageVector? = null
