package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.EditOutline: ImageVector
    get() {
        if (_EditOutline != null) {
            return _EditOutline!!
        }
        _EditOutline = ImageVector.Builder(
            name = "EditOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5f, 19f)
                horizontalLineToRelative(1.425f)
                lineTo(16.2f, 9.225f)
                lineTo(14.775f, 7.8f)
                lineTo(5f, 17.575f)
                close()
                moveTo(3f, 21f)
                verticalLineToRelative(-4.25f)
                lineTo(16.2f, 3.575f)
                quadToRelative(0.3f, -0.275f, 0.663f, -0.425f)
                reflectiveQuadToRelative(0.762f, -0.15f)
                reflectiveQuadToRelative(0.775f, 0.15f)
                reflectiveQuadToRelative(0.65f, 0.45f)
                lineTo(20.425f, 5f)
                quadToRelative(0.3f, 0.275f, 0.438f, 0.65f)
                reflectiveQuadTo(21f, 6.4f)
                quadToRelative(0f, 0.4f, -0.137f, 0.763f)
                reflectiveQuadToRelative(-0.438f, 0.662f)
                lineTo(7.25f, 21f)
                close()
                moveTo(19f, 6.4f)
                lineTo(17.6f, 5f)
                close()
                moveTo(15.475f, 8.525f)
                lineToRelative(-0.7f, -0.725f)
                lineTo(16.2f, 9.225f)
                close()
            }
        }.build()

        return _EditOutline!!
    }

@Suppress("ObjectPropertyName")
private var _EditOutline: ImageVector? = null
