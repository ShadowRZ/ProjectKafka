package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.ShieldOutline: ImageVector
    get() {
        if (_ShieldOutline != null) {
            return _ShieldOutline!!
        }
        _ShieldOutline = ImageVector.Builder(
            name = "ShieldOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 22f)
                quadToRelative(-3.475f, -0.875f, -5.738f, -3.988f)
                reflectiveQuadTo(4f, 11.1f)
                verticalLineTo(5f)
                lineToRelative(8f, -3f)
                lineToRelative(8f, 3f)
                verticalLineToRelative(6.1f)
                quadToRelative(0f, 3.8f, -2.262f, 6.913f)
                reflectiveQuadTo(12f, 22f)
                moveToRelative(0f, -2.1f)
                quadToRelative(2.6f, -0.825f, 4.3f, -3.3f)
                reflectiveQuadToRelative(1.7f, -5.5f)
                verticalLineTo(6.375f)
                lineToRelative(-6f, -2.25f)
                lineToRelative(-6f, 2.25f)
                verticalLineTo(11.1f)
                quadToRelative(0f, 3.025f, 1.7f, 5.5f)
                reflectiveQuadToRelative(4.3f, 3.3f)
                moveToRelative(0f, -7.9f)
            }
        }.build()

        return _ShieldOutline!!
    }

@Suppress("ObjectPropertyName")
private var _ShieldOutline: ImageVector? = null
