package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.SettingsOutline: ImageVector
    get() {
        if (_SettingsOutline != null) {
            return _SettingsOutline!!
        }
        _SettingsOutline = ImageVector.Builder(
            name = "SettingsOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(9.25f, 22f)
                lineToRelative(-0.4f, -3.2f)
                quadToRelative(-0.325f, -0.125f, -0.612f, -0.3f)
                reflectiveQuadToRelative(-0.563f, -0.375f)
                lineTo(4.7f, 19.375f)
                lineToRelative(-2.75f, -4.75f)
                lineToRelative(2.575f, -1.95f)
                quadTo(4.5f, 12.5f, 4.5f, 12.338f)
                verticalLineToRelative(-0.675f)
                quadToRelative(0f, -0.163f, 0.025f, -0.338f)
                lineTo(1.95f, 9.375f)
                lineToRelative(2.75f, -4.75f)
                lineToRelative(2.975f, 1.25f)
                quadToRelative(0.275f, -0.2f, 0.575f, -0.375f)
                reflectiveQuadToRelative(0.6f, -0.3f)
                lineToRelative(0.4f, -3.2f)
                horizontalLineToRelative(5.5f)
                lineToRelative(0.4f, 3.2f)
                quadToRelative(0.325f, 0.125f, 0.613f, 0.3f)
                reflectiveQuadToRelative(0.562f, 0.375f)
                lineToRelative(2.975f, -1.25f)
                lineToRelative(2.75f, 4.75f)
                lineToRelative(-2.575f, 1.95f)
                quadToRelative(0.025f, 0.175f, 0.025f, 0.338f)
                verticalLineToRelative(0.674f)
                quadToRelative(0f, 0.163f, -0.05f, 0.338f)
                lineToRelative(2.575f, 1.95f)
                lineToRelative(-2.75f, 4.75f)
                lineToRelative(-2.95f, -1.25f)
                quadToRelative(-0.275f, 0.2f, -0.575f, 0.375f)
                reflectiveQuadToRelative(-0.6f, 0.3f)
                lineToRelative(-0.4f, 3.2f)
                close()
                moveTo(11f, 20f)
                horizontalLineToRelative(1.975f)
                lineToRelative(0.35f, -2.65f)
                quadToRelative(0.775f, -0.2f, 1.438f, -0.587f)
                reflectiveQuadToRelative(1.212f, -0.938f)
                lineToRelative(2.475f, 1.025f)
                lineToRelative(0.975f, -1.7f)
                lineToRelative(-2.15f, -1.625f)
                quadToRelative(0.125f, -0.35f, 0.175f, -0.737f)
                reflectiveQuadTo(17.5f, 12f)
                reflectiveQuadToRelative(-0.05f, -0.787f)
                reflectiveQuadToRelative(-0.175f, -0.738f)
                lineToRelative(2.15f, -1.625f)
                lineToRelative(-0.975f, -1.7f)
                lineToRelative(-2.475f, 1.05f)
                quadToRelative(-0.55f, -0.575f, -1.212f, -0.962f)
                reflectiveQuadToRelative(-1.438f, -0.588f)
                lineTo(13f, 4f)
                horizontalLineToRelative(-1.975f)
                lineToRelative(-0.35f, 2.65f)
                quadToRelative(-0.775f, 0.2f, -1.437f, 0.588f)
                reflectiveQuadToRelative(-1.213f, 0.937f)
                lineTo(5.55f, 7.15f)
                lineToRelative(-0.975f, 1.7f)
                lineToRelative(2.15f, 1.6f)
                quadToRelative(-0.125f, 0.375f, -0.175f, 0.75f)
                reflectiveQuadToRelative(-0.05f, 0.8f)
                quadToRelative(0f, 0.4f, 0.05f, 0.775f)
                reflectiveQuadToRelative(0.175f, 0.75f)
                lineToRelative(-2.15f, 1.625f)
                lineToRelative(0.975f, 1.7f)
                lineToRelative(2.475f, -1.05f)
                quadToRelative(0.55f, 0.575f, 1.213f, 0.963f)
                reflectiveQuadToRelative(1.437f, 0.587f)
                close()
                moveTo(12.05f, 15.5f)
                quadToRelative(1.45f, 0f, 2.475f, -1.025f)
                reflectiveQuadTo(15.55f, 12f)
                reflectiveQuadToRelative(-1.025f, -2.475f)
                reflectiveQuadTo(12.05f, 8.5f)
                quadToRelative(-1.475f, 0f, -2.488f, 1.025f)
                reflectiveQuadTo(8.55f, 12f)
                reflectiveQuadToRelative(1.013f, 2.475f)
                reflectiveQuadTo(12.05f, 15.5f)
                moveTo(12f, 12f)
            }
        }.build()

        return _SettingsOutline!!
    }

@Suppress("ObjectPropertyName")
private var _SettingsOutline: ImageVector? = null
