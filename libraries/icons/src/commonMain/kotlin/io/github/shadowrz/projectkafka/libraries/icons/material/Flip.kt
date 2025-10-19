package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Flip: ImageVector
    get() {
        if (_Flip != null) {
            return _Flip!!
        }
        _Flip = ImageVector.Builder(
            name = "Flip",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9f, 21f)
                lineTo(5f, 21f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(3f, 19f)
                lineTo(3f, 5f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(5f, 3f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(2f)
                lineTo(5f, 5f)
                verticalLineToRelative(14f)
                horizontalLineToRelative(4f)
                close()
                moveTo(11f, 23f)
                lineTo(11f, 1f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(22f)
                close()
                moveTo(15f, 21f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
                moveTo(15f, 5f)
                lineTo(15f, 3f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
                moveTo(19f, 21f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(19f, 21f)
                moveToRelative(0f, -4f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
                moveTo(19f, 13f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
                moveTo(19f, 9f)
                lineTo(19f, 7f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
                moveTo(19f, 5f)
                lineTo(19f, 3f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(21f, 5f)
                close()
            }
        }.build()

        return _Flip!!
    }

@Suppress("ObjectPropertyName")
private var _Flip: ImageVector? = null
