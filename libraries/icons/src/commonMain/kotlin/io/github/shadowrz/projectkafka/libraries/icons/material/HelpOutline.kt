package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.HelpOutline: ImageVector
    get() {
        if (_HelpOutline != null) {
            return _HelpOutline!!
        }
        _HelpOutline = ImageVector.Builder(
            name = "HelpOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.95f, 18f)
                quadToRelative(0.525f, 0f, 0.888f, -0.363f)
                reflectiveQuadToRelative(0.362f, -0.887f)
                reflectiveQuadToRelative(-0.362f, -0.888f)
                reflectiveQuadToRelative(-0.888f, -0.362f)
                reflectiveQuadToRelative(-0.887f, 0.363f)
                reflectiveQuadToRelative(-0.363f, 0.887f)
                reflectiveQuadToRelative(0.363f, 0.888f)
                reflectiveQuadToRelative(0.887f, 0.362f)
                moveToRelative(-0.9f, -3.85f)
                horizontalLineToRelative(1.85f)
                quadToRelative(0f, -0.825f, 0.188f, -1.3f)
                reflectiveQuadToRelative(1.062f, -1.3f)
                quadToRelative(0.65f, -0.65f, 1.025f, -1.238f)
                reflectiveQuadTo(15.55f, 8.9f)
                quadToRelative(0f, -1.4f, -1.025f, -2.15f)
                reflectiveQuadTo(12.1f, 6f)
                quadToRelative(-1.425f, 0f, -2.312f, 0.75f)
                reflectiveQuadTo(8.55f, 8.55f)
                lineToRelative(1.65f, 0.65f)
                quadToRelative(0.125f, -0.45f, 0.563f, -0.975f)
                reflectiveQuadTo(12.1f, 7.7f)
                quadToRelative(0.8f, 0f, 1.2f, 0.438f)
                reflectiveQuadToRelative(0.4f, 0.962f)
                quadToRelative(0f, 0.5f, -0.3f, 0.938f)
                reflectiveQuadToRelative(-0.75f, 0.812f)
                quadToRelative(-1.1f, 0.975f, -1.35f, 1.475f)
                reflectiveQuadToRelative(-0.25f, 1.825f)
                moveTo(12f, 22f)
                quadToRelative(-2.075f, 0f, -3.9f, -0.787f)
                reflectiveQuadToRelative(-3.175f, -2.138f)
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
                reflectiveQuadToRelative(-2.325f, -5.675f)
                reflectiveQuadTo(12f, 4f)
                reflectiveQuadTo(6.325f, 6.325f)
                reflectiveQuadTo(4f, 12f)
                reflectiveQuadToRelative(2.325f, 5.675f)
                reflectiveQuadTo(12f, 20f)
                moveToRelative(0f, -8f)
            }
        }.build()

        return _HelpOutline!!
    }

@Suppress("ObjectPropertyName")
private var _HelpOutline: ImageVector? = null
