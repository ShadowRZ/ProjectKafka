package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.SwitchAccountOutline: ImageVector
    get() {
        if (_SwitchAccountOutline != null) {
            return _SwitchAccountOutline!!
        }
        _SwitchAccountOutline = ImageVector.Builder(
            name = "SwitchAccountOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(14f, 11f)
                quadToRelative(1.25f, 0f, 2.125f, -0.875f)
                reflectiveQuadTo(17f, 8f)
                reflectiveQuadToRelative(-0.875f, -2.125f)
                reflectiveQuadTo(14f, 5f)
                reflectiveQuadToRelative(-2.125f, 0.875f)
                reflectiveQuadTo(11f, 8f)
                reflectiveQuadToRelative(0.875f, 2.125f)
                reflectiveQuadTo(14f, 11f)
                moveToRelative(-6f, 4.75f)
                quadToRelative(1.125f, -1.325f, 2.7f, -2.037f)
                reflectiveQuadTo(14f, 13f)
                reflectiveQuadToRelative(3.3f, 0.713f)
                reflectiveQuadTo(20f, 15.75f)
                lineTo(20f, 4f)
                lineTo(8f, 4f)
                close()
                moveTo(8f, 18f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(6f, 16f)
                lineTo(6f, 4f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(8f, 2f)
                horizontalLineToRelative(12f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(22f, 4f)
                verticalLineToRelative(12f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(20f, 18f)
                close()
                moveTo(4f, 22f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(2f, 20f)
                lineTo(2f, 6f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(14f)
                horizontalLineToRelative(14f)
                verticalLineToRelative(2f)
                close()
                moveTo(14f, 9f)
                quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
                reflectiveQuadTo(13f, 8f)
                reflectiveQuadToRelative(0.288f, -0.712f)
                reflectiveQuadTo(14f, 7f)
                reflectiveQuadToRelative(0.713f, 0.288f)
                reflectiveQuadTo(15f, 8f)
                reflectiveQuadToRelative(-0.288f, 0.713f)
                reflectiveQuadTo(14f, 9f)
                moveToRelative(-3.3f, 7f)
                horizontalLineToRelative(6.6f)
                quadToRelative(-0.725f, -0.5f, -1.575f, -0.75f)
                reflectiveQuadTo(14f, 15f)
                reflectiveQuadToRelative(-1.725f, 0.25f)
                reflectiveQuadTo(10.7f, 16f)
                moveTo(14f, 9.875f)
            }
        }.build()

        return _SwitchAccountOutline!!
    }

@Suppress("ObjectPropertyName")
private var _SwitchAccountOutline: ImageVector? = null
