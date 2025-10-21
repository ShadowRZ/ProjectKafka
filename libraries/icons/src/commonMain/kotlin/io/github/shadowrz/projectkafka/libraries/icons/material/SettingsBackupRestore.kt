package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.SettingsBackupRestore: ImageVector
    get() {
        if (_SettingsBackupRestore != null) {
            return _SettingsBackupRestore!!
        }
        _SettingsBackupRestore = ImageVector.Builder(
            name = "SettingsBackupRestore",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 14f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(10f, 12f)
                reflectiveQuadToRelative(0.588f, -1.412f)
                reflectiveQuadTo(12f, 10f)
                reflectiveQuadToRelative(1.413f, 0.588f)
                reflectiveQuadTo(14f, 12f)
                reflectiveQuadToRelative(-0.587f, 1.413f)
                reflectiveQuadTo(12f, 14f)
                moveToRelative(0f, 7f)
                quadToRelative(-3.475f, 0f, -6.025f, -2.287f)
                reflectiveQuadTo(3.05f, 13f)
                horizontalLineTo(5.1f)
                quadToRelative(0.35f, 2.6f, 2.313f, 4.3f)
                reflectiveQuadTo(12f, 19f)
                quadToRelative(2.925f, 0f, 4.963f, -2.037f)
                reflectiveQuadTo(19f, 12f)
                reflectiveQuadToRelative(-2.037f, -4.962f)
                reflectiveQuadTo(12f, 5f)
                quadToRelative(-1.725f, 0f, -3.225f, 0.8f)
                reflectiveQuadTo(6.25f, 8f)
                horizontalLineTo(9f)
                verticalLineToRelative(2f)
                horizontalLineTo(3f)
                verticalLineTo(4f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2.35f)
                quadToRelative(1.275f, -1.6f, 3.113f, -2.475f)
                reflectiveQuadTo(12f, 3f)
                quadToRelative(1.875f, 0f, 3.513f, 0.713f)
                reflectiveQuadToRelative(2.85f, 1.924f)
                reflectiveQuadToRelative(1.925f, 2.85f)
                reflectiveQuadTo(21f, 12f)
                reflectiveQuadToRelative(-0.712f, 3.513f)
                reflectiveQuadToRelative(-1.925f, 2.85f)
                reflectiveQuadToRelative(-2.85f, 1.925f)
                reflectiveQuadTo(12f, 21f)
            }
        }.build()

        return _SettingsBackupRestore!!
    }

@Suppress("ObjectPropertyName")
private var _SettingsBackupRestore: ImageVector? = null
