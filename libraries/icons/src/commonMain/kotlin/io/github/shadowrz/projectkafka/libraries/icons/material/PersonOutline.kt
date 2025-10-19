package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.PersonOutline: ImageVector
    get() {
        if (_PersonOutline != null) {
            return _PersonOutline!!
        }
        _PersonOutline = ImageVector.Builder(
            name = "PersonOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 12f)
                quadToRelative(-1.65f, 0f, -2.825f, -1.175f)
                reflectiveQuadTo(8f, 8f)
                reflectiveQuadToRelative(1.175f, -2.825f)
                reflectiveQuadTo(12f, 4f)
                reflectiveQuadToRelative(2.825f, 1.175f)
                reflectiveQuadTo(16f, 8f)
                reflectiveQuadToRelative(-1.175f, 2.825f)
                reflectiveQuadTo(12f, 12f)
                moveToRelative(-8f, 8f)
                verticalLineToRelative(-2.8f)
                quadToRelative(0f, -0.85f, 0.438f, -1.562f)
                reflectiveQuadTo(5.6f, 14.55f)
                quadToRelative(1.55f, -0.775f, 3.15f, -1.162f)
                reflectiveQuadTo(12f, 13f)
                reflectiveQuadToRelative(3.25f, 0.388f)
                reflectiveQuadToRelative(3.15f, 1.162f)
                quadToRelative(0.725f, 0.375f, 1.163f, 1.088f)
                reflectiveQuadTo(20f, 17.2f)
                lineTo(20f, 20f)
                close()
                moveTo(6f, 18f)
                horizontalLineToRelative(12f)
                verticalLineToRelative(-0.8f)
                quadToRelative(0f, -0.275f, -0.137f, -0.5f)
                reflectiveQuadToRelative(-0.363f, -0.35f)
                quadToRelative(-1.35f, -0.675f, -2.725f, -1.012f)
                reflectiveQuadTo(12f, 15f)
                reflectiveQuadToRelative(-2.775f, 0.338f)
                reflectiveQuadTo(6.5f, 16.35f)
                quadToRelative(-0.225f, 0.125f, -0.363f, 0.35f)
                reflectiveQuadTo(6f, 17.2f)
                close()
                moveTo(12f, 10f)
                quadToRelative(0.825f, 0f, 1.413f, -0.587f)
                reflectiveQuadTo(14f, 8f)
                reflectiveQuadToRelative(-0.587f, -1.412f)
                reflectiveQuadTo(12f, 6f)
                reflectiveQuadToRelative(-1.412f, 0.588f)
                reflectiveQuadTo(10f, 8f)
                reflectiveQuadToRelative(0.588f, 1.413f)
                reflectiveQuadTo(12f, 10f)
                moveToRelative(0f, 8f)
            }
        }.build()

        return _PersonOutline!!
    }

@Suppress("ObjectPropertyName")
private var _PersonOutline: ImageVector? = null
