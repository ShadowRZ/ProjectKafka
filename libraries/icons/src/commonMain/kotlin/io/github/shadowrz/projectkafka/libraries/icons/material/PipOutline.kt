package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.PipOutline: ImageVector
    get() {
        if (_PipOutline != null) {
            return _PipOutline!!
        }
        _PipOutline = ImageVector.Builder(
            name = "PipOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(2f, 11f)
                lineTo(2f, 9f)
                horizontalLineToRelative(3.6f)
                lineTo(1.3f, 4.7f)
                lineToRelative(1.4f, -1.4f)
                lineTo(7f, 7.6f)
                lineTo(7f, 4f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(7f)
                close()
                moveTo(4f, 20f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(2f, 18f)
                verticalLineToRelative(-5f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(2f)
                close()
                moveTo(20f, 13f)
                lineTo(20f, 6f)
                horizontalLineToRelative(-9f)
                lineTo(11f, 4f)
                horizontalLineToRelative(9f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(22f, 6f)
                verticalLineToRelative(7f)
                close()
                moveTo(14f, 20f)
                verticalLineToRelative(-5f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(5f)
                close()
            }
        }.build()

        return _PipOutline!!
    }

@Suppress("ObjectPropertyName")
private var _PipOutline: ImageVector? = null
