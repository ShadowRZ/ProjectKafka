package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.NoteStackOutline: ImageVector
    get() {
        if (_NoteStackOutline != null) {
            return _NoteStackOutline!!
        }
        _NoteStackOutline = ImageVector.Builder(
            name = "NoteStackOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7f, 20f)
                lineTo(7f, 8.975f)
                quadToRelative(0f, -0.825f, 0.6f, -1.4f)
                reflectiveQuadTo(9.025f, 7f)
                lineTo(20f, 7f)
                quadToRelative(0.825f, 0f, 1.413f, 0.587f)
                reflectiveQuadTo(22f, 9f)
                verticalLineToRelative(8f)
                lineToRelative(-5f, 5f)
                lineTo(9f, 22f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(7f, 20f)
                moveTo(2.025f, 6.25f)
                quadToRelative(-0.15f, -0.825f, 0.325f, -1.487f)
                reflectiveQuadToRelative(1.3f, -0.813f)
                lineTo(14.5f, 2.025f)
                quadToRelative(0.825f, -0.15f, 1.488f, 0.325f)
                reflectiveQuadToRelative(0.812f, 1.3f)
                lineTo(17.05f, 5f)
                lineTo(15f, 5f)
                lineToRelative(-0.175f, -1f)
                lineTo(4f, 5.925f)
                lineToRelative(1f, 5.65f)
                verticalLineToRelative(6.975f)
                quadToRelative(-0.4f, -0.225f, -0.687f, -0.6f)
                reflectiveQuadToRelative(-0.363f, -0.85f)
                close()
                moveTo(9f, 9f)
                verticalLineToRelative(11f)
                horizontalLineToRelative(7f)
                verticalLineToRelative(-4f)
                horizontalLineToRelative(4f)
                lineTo(20f, 9f)
                close()
                moveTo(14.5f, 14.5f)
            }
        }.build()

        return _NoteStackOutline!!
    }

@Suppress("ObjectPropertyName")
private var _NoteStackOutline: ImageVector? = null
