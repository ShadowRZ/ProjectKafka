package io.github.shadowrz.projectkafka.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons

val KafkaIcons.SendOutline: ImageVector
    get() {
        if (_SendOutline != null) {
            return _SendOutline!!
        }
        _SendOutline =
            ImageVector.Builder(
                    name = "SendOutline",
                    defaultWidth = 32.dp,
                    defaultHeight = 32.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(3f, 20f)
                        lineTo(3f, 4f)
                        lineToRelative(19f, 8f)
                        close()
                        moveTo(5f, 17f)
                        lineToRelative(11.85f, -5f)
                        lineTo(5f, 7f)
                        verticalLineToRelative(3.5f)
                        lineToRelative(6f, 1.5f)
                        lineToRelative(-6f, 1.5f)
                        close()
                        moveTo(5f, 17f)
                        lineTo(5f, 7f)
                        close()
                    }
                }
                .build()

        return _SendOutline!!
    }

@Suppress("ObjectPropertyName") private var _SendOutline: ImageVector? = null
