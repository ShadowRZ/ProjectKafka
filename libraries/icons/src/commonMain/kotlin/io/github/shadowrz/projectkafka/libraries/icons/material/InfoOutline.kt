package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.InfoOutline: ImageVector
    get() {
        if (_InfoOutline != null) {
            return _InfoOutline!!
        }
        _InfoOutline = ImageVector.Builder(
            name = "InfoOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11f, 17f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(-2f)
                close()
                moveTo(12f, 9f)
                quadToRelative(0.425f, 0f, 0.713f, -0.288f)
                reflectiveQuadTo(13f, 8f)
                reflectiveQuadToRelative(-0.288f, -0.712f)
                reflectiveQuadTo(12f, 7f)
                reflectiveQuadToRelative(-0.712f, 0.288f)
                reflectiveQuadTo(11f, 8f)
                reflectiveQuadToRelative(0.288f, 0.713f)
                reflectiveQuadTo(12f, 9f)
                moveToRelative(0f, 13f)
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
                reflectiveQuadToRelative(-2.325f, -5.675f)
                reflectiveQuadTo(12f, 4f)
                reflectiveQuadTo(6.325f, 6.325f)
                reflectiveQuadTo(4f, 12f)
                reflectiveQuadToRelative(2.325f, 5.675f)
                reflectiveQuadTo(12f, 20f)
                moveToRelative(0f, -8f)
            }
        }.build()

        return _InfoOutline!!
    }

@Suppress("ObjectPropertyName")
private var _InfoOutline: ImageVector? = null
