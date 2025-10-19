package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Translate: ImageVector
    get() {
        if (_Translate != null) {
            return _Translate!!
        }
        _Translate = ImageVector.Builder(
            name = "Translate",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(11.9f, 22f)
                lineToRelative(4.55f, -12f)
                horizontalLineToRelative(2.1f)
                lineToRelative(4.55f, 12f)
                lineTo(21f, 22f)
                lineToRelative(-1.075f, -3.05f)
                horizontalLineToRelative(-4.85f)
                lineTo(14f, 22f)
                close()
                moveTo(4f, 19f)
                lineToRelative(-1.4f, -1.4f)
                lineToRelative(5.05f, -5.05f)
                quadToRelative(-0.875f, -0.875f, -1.588f, -2f)
                reflectiveQuadTo(4.75f, 8f)
                horizontalLineToRelative(2.1f)
                quadToRelative(0.5f, 0.975f, 1f, 1.7f)
                reflectiveQuadToRelative(1.2f, 1.45f)
                quadToRelative(0.825f, -0.825f, 1.713f, -2.313f)
                reflectiveQuadTo(12.1f, 6f)
                lineTo(1f, 6f)
                lineTo(1f, 4f)
                horizontalLineToRelative(7f)
                lineTo(8f, 2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(7f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-2.9f)
                quadToRelative(-0.525f, 1.8f, -1.575f, 3.7f)
                reflectiveQuadToRelative(-2.075f, 2.9f)
                lineToRelative(2.4f, 2.45f)
                lineToRelative(-0.75f, 2.05f)
                lineToRelative(-3.05f, -3.125f)
                close()
                moveTo(15.7f, 17.2f)
                horizontalLineToRelative(3.6f)
                lineToRelative(-1.8f, -5.1f)
                close()
            }
        }.build()

        return _Translate!!
    }

@Suppress("ObjectPropertyName")
private var _Translate: ImageVector? = null
