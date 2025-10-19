package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.RestorePageOutline: ImageVector
    get() {
        if (_RestorePageOutline != null) {
            return _RestorePageOutline!!
        }
        _RestorePageOutline = ImageVector.Builder(
            name = "RestorePageOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 17.75f)
                quadToRelative(1.95f, 0f, 3.35f, -1.4f)
                reflectiveQuadToRelative(1.4f, -3.35f)
                reflectiveQuadToRelative(-1.4f, -3.35f)
                reflectiveQuadTo(12f, 8.25f)
                quadToRelative(-0.95f, 0f, -1.775f, 0.35f)
                reflectiveQuadToRelative(-1.475f, 0.95f)
                lineTo(8.75f, 8f)
                horizontalLineToRelative(-1.5f)
                verticalLineToRelative(4.25f)
                horizontalLineToRelative(4.25f)
                verticalLineToRelative(-1.5f)
                lineTo(9.7f, 10.75f)
                quadToRelative(0.425f, -0.45f, 1.025f, -0.725f)
                reflectiveQuadTo(12f, 9.75f)
                quadToRelative(1.35f, 0f, 2.3f, 0.95f)
                reflectiveQuadToRelative(0.95f, 2.3f)
                reflectiveQuadToRelative(-0.95f, 2.3f)
                reflectiveQuadToRelative(-2.3f, 0.95f)
                quadToRelative(-1.1f, 0f, -1.925f, -0.638f)
                reflectiveQuadTo(8.9f, 14f)
                lineTo(7.35f, 14f)
                quadToRelative(0.35f, 1.625f, 1.638f, 2.688f)
                reflectiveQuadTo(12f, 17.75f)
                moveTo(6f, 22f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(4f, 20f)
                lineTo(4f, 4f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(6f, 2f)
                horizontalLineToRelative(8f)
                lineToRelative(6f, 6f)
                verticalLineToRelative(12f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(18f, 22f)
                close()
                moveTo(6f, 20f)
                horizontalLineToRelative(12f)
                lineTo(18f, 8.85f)
                lineTo(13.15f, 4f)
                lineTo(6f, 4f)
                close()
                moveTo(6f, 20f)
                lineTo(6f, 4f)
                close()
            }
        }.build()

        return _RestorePageOutline!!
    }

@Suppress("ObjectPropertyName")
private var _RestorePageOutline: ImageVector? = null
