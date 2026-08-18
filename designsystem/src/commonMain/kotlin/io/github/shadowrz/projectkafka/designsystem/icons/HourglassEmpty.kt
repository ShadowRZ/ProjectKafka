package io.github.shadowrz.projectkafka.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons

val KafkaIcons.HourglassEmpty: ImageVector
    get() {
        if (_HourglassEmpty != null) {
            return _HourglassEmpty!!
        }
        _HourglassEmpty =
            ImageVector.Builder(
                    name = "HourglassEmpty",
                    defaultWidth = 32.dp,
                    defaultHeight = 32.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(8f, 20f)
                        horizontalLineToRelative(8f)
                        verticalLineToRelative(-3f)
                        quadToRelative(0f, -1.65f, -1.175f, -2.825f)
                        reflectiveQuadTo(12f, 13f)
                        reflectiveQuadToRelative(-2.825f, 1.175f)
                        reflectiveQuadTo(8f, 17f)
                        close()
                        moveTo(12f, 11f)
                        quadToRelative(1.65f, 0f, 2.825f, -1.175f)
                        reflectiveQuadTo(16f, 7f)
                        lineTo(16f, 4f)
                        lineTo(8f, 4f)
                        verticalLineToRelative(3f)
                        quadToRelative(0f, 1.65f, 1.175f, 2.825f)
                        reflectiveQuadTo(12f, 11f)
                        moveTo(4f, 22f)
                        verticalLineToRelative(-2f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(-3f)
                        quadToRelative(0f, -1.525f, 0.713f, -2.863f)
                        reflectiveQuadTo(8.7f, 12f)
                        quadToRelative(-1.275f, -0.8f, -1.987f, -2.137f)
                        reflectiveQuadTo(6f, 7f)
                        lineTo(6f, 4f)
                        lineTo(4f, 4f)
                        lineTo(4f, 2f)
                        horizontalLineToRelative(16f)
                        verticalLineToRelative(2f)
                        horizontalLineToRelative(-2f)
                        verticalLineToRelative(3f)
                        quadToRelative(0f, 1.525f, -0.712f, 2.863f)
                        reflectiveQuadTo(15.3f, 12f)
                        quadToRelative(1.275f, 0.8f, 1.988f, 2.138f)
                        reflectiveQuadTo(18f, 17f)
                        verticalLineToRelative(3f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        close()
                    }
                }
                .build()

        return _HourglassEmpty!!
    }

@Suppress("ObjectPropertyName") private var _HourglassEmpty: ImageVector? = null
