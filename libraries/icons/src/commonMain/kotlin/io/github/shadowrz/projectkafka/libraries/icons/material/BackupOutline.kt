package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.BackupOutline: ImageVector
    get() {
        if (_BackupOutline != null) {
            return _BackupOutline!!
        }
        _BackupOutline = ImageVector.Builder(
            name = "BackupOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.5f, 20f)
                quadToRelative(-2.275f, 0f, -3.887f, -1.575f)
                reflectiveQuadTo(1f, 14.575f)
                quadToRelative(0f, -1.95f, 1.175f, -3.475f)
                reflectiveQuadTo(5.25f, 9.15f)
                quadToRelative(0.625f, -2.3f, 2.5f, -3.725f)
                reflectiveQuadTo(12f, 4f)
                quadToRelative(2.925f, 0f, 4.963f, 2.038f)
                reflectiveQuadTo(19f, 11f)
                quadToRelative(1.725f, 0.2f, 2.863f, 1.488f)
                reflectiveQuadTo(23f, 15.5f)
                quadToRelative(0f, 1.875f, -1.312f, 3.188f)
                reflectiveQuadTo(18.5f, 20f)
                lineTo(13f, 20f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(11f, 18f)
                verticalLineToRelative(-5.15f)
                lineTo(9.4f, 14.4f)
                lineTo(8f, 13f)
                lineToRelative(4f, -4f)
                lineToRelative(4f, 4f)
                lineToRelative(-1.4f, 1.4f)
                lineToRelative(-1.6f, -1.55f)
                lineTo(13f, 18f)
                horizontalLineToRelative(5.5f)
                quadToRelative(1.05f, 0f, 1.775f, -0.725f)
                reflectiveQuadTo(21f, 15.5f)
                reflectiveQuadToRelative(-0.725f, -1.775f)
                reflectiveQuadTo(18.5f, 13f)
                lineTo(17f, 13f)
                verticalLineToRelative(-2f)
                quadToRelative(0f, -2.075f, -1.463f, -3.538f)
                reflectiveQuadTo(12f, 6f)
                reflectiveQuadTo(8.463f, 7.463f)
                reflectiveQuadTo(7f, 11f)
                horizontalLineToRelative(-0.5f)
                quadToRelative(-1.45f, 0f, -2.475f, 1.025f)
                reflectiveQuadTo(3f, 14.5f)
                reflectiveQuadToRelative(1.025f, 2.475f)
                reflectiveQuadTo(6.5f, 18f)
                lineTo(9f, 18f)
                verticalLineToRelative(2f)
                close()
                moveTo(12f, 13f)
            }
        }.build()

        return _BackupOutline!!
    }

@Suppress("ObjectPropertyName")
private var _BackupOutline: ImageVector? = null
