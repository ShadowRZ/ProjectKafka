package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.DashboardOutline: ImageVector
    get() {
        if (_DashboardOutline != null) {
            return _DashboardOutline!!
        }
        _DashboardOutline = ImageVector.Builder(
            name = "DashboardOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(13f, 9f)
                lineTo(13f, 3f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(6f)
                close()
                moveTo(3f, 13f)
                lineTo(3f, 3f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(10f)
                close()
                moveTo(13f, 21f)
                lineTo(13f, 11f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(10f)
                close()
                moveTo(3f, 21f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(6f)
                close()
                moveTo(5f, 11f)
                horizontalLineToRelative(4f)
                lineTo(9f, 5f)
                lineTo(5f, 5f)
                close()
                moveTo(15f, 19f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(-4f)
                close()
                moveTo(15f, 7f)
                horizontalLineToRelative(4f)
                lineTo(19f, 5f)
                horizontalLineToRelative(-4f)
                close()
                moveTo(5f, 19f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(-2f)
                lineTo(5f, 17f)
                close()
                moveTo(9f, 17f)
            }
        }.build()

        return _DashboardOutline!!
    }

@Suppress("ObjectPropertyName")
private var _DashboardOutline: ImageVector? = null
