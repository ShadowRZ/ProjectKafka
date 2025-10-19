package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Group: ImageVector
    get() {
        if (_Group != null) {
            return _Group!!
        }
        _Group = ImageVector.Builder(
            name = "Group",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(1f, 20f)
                verticalLineToRelative(-2.8f)
                quadToRelative(0f, -0.85f, 0.438f, -1.562f)
                reflectiveQuadTo(2.6f, 14.55f)
                quadToRelative(1.55f, -0.775f, 3.15f, -1.162f)
                reflectiveQuadTo(9f, 13f)
                reflectiveQuadToRelative(3.25f, 0.388f)
                reflectiveQuadToRelative(3.15f, 1.162f)
                quadToRelative(0.725f, 0.375f, 1.163f, 1.088f)
                reflectiveQuadTo(17f, 17.2f)
                lineTo(17f, 20f)
                close()
                moveTo(19f, 20f)
                verticalLineToRelative(-3f)
                quadToRelative(0f, -1.1f, -0.612f, -2.113f)
                reflectiveQuadTo(16.65f, 13.15f)
                quadToRelative(1.275f, 0.15f, 2.4f, 0.513f)
                reflectiveQuadToRelative(2.1f, 0.887f)
                quadToRelative(0.9f, 0.5f, 1.375f, 1.112f)
                reflectiveQuadTo(23f, 17f)
                verticalLineToRelative(3f)
                close()
                moveTo(9f, 12f)
                quadToRelative(-1.65f, 0f, -2.825f, -1.175f)
                reflectiveQuadTo(5f, 8f)
                reflectiveQuadToRelative(1.175f, -2.825f)
                reflectiveQuadTo(9f, 4f)
                reflectiveQuadToRelative(2.825f, 1.175f)
                reflectiveQuadTo(13f, 8f)
                reflectiveQuadToRelative(-1.175f, 2.825f)
                reflectiveQuadTo(9f, 12f)
                moveToRelative(10f, -4f)
                quadToRelative(0f, 1.65f, -1.175f, 2.825f)
                reflectiveQuadTo(15f, 12f)
                quadToRelative(-0.275f, 0f, -0.7f, -0.062f)
                reflectiveQuadToRelative(-0.7f, -0.138f)
                quadToRelative(0.675f, -0.8f, 1.038f, -1.775f)
                reflectiveQuadTo(15f, 8f)
                reflectiveQuadToRelative(-0.362f, -2.025f)
                reflectiveQuadTo(13.6f, 4.2f)
                quadToRelative(0.35f, -0.125f, 0.7f, -0.163f)
                reflectiveQuadTo(15f, 4f)
                quadToRelative(1.65f, 0f, 2.825f, 1.175f)
                reflectiveQuadTo(19f, 8f)
            }
        }.build()

        return _Group!!
    }

@Suppress("ObjectPropertyName")
private var _Group: ImageVector? = null
