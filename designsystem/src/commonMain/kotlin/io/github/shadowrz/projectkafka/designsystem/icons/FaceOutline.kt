package io.github.shadowrz.projectkafka.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons

val KafkaIcons.FaceOutline: ImageVector
    get() {
        if (_FaceOutline != null) {
            return _FaceOutline!!
        }
        _FaceOutline =
            ImageVector.Builder(
                    name = "FaceOutline",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(9f, 14.25f)
                        quadToRelative(-0.525f, 0f, -0.888f, -0.363f)
                        reflectiveQuadTo(7.75f, 13f)
                        reflectiveQuadToRelative(0.363f, -0.888f)
                        reflectiveQuadTo(9f, 11.75f)
                        reflectiveQuadToRelative(0.888f, 0.363f)
                        reflectiveQuadToRelative(0.362f, 0.887f)
                        reflectiveQuadToRelative(-0.363f, 0.888f)
                        reflectiveQuadTo(9f, 14.25f)
                        moveToRelative(6f, 0f)
                        quadToRelative(-0.525f, 0f, -0.888f, -0.363f)
                        reflectiveQuadTo(13.75f, 13f)
                        reflectiveQuadToRelative(0.363f, -0.888f)
                        reflectiveQuadToRelative(0.887f, -0.362f)
                        reflectiveQuadToRelative(0.888f, 0.363f)
                        reflectiveQuadToRelative(0.362f, 0.887f)
                        reflectiveQuadToRelative(-0.363f, 0.888f)
                        reflectiveQuadToRelative(-0.887f, 0.362f)
                        moveTo(12f, 20f)
                        quadToRelative(3.35f, 0f, 5.675f, -2.325f)
                        reflectiveQuadTo(20f, 12f)
                        quadToRelative(0f, -0.6f, -0.075f, -1.162f)
                        reflectiveQuadTo(19.65f, 9.75f)
                        quadToRelative(-0.525f, 0.125f, -1.05f, 0.188f)
                        reflectiveQuadTo(17.5f, 10f)
                        quadToRelative(-2.275f, 0f, -4.3f, -0.975f)
                        reflectiveQuadTo(9.75f, 6.3f)
                        quadToRelative(-0.8f, 1.95f, -2.287f, 3.388f)
                        reflectiveQuadTo(4f, 11.85f)
                        verticalLineTo(12f)
                        quadToRelative(0f, 3.35f, 2.325f, 5.675f)
                        reflectiveQuadTo(12f, 20f)
                        moveToRelative(0f, 2f)
                        quadToRelative(-2.075f, 0f, -3.9f, -0.787f)
                        reflectiveQuadToRelative(-3.175f, -2.138f)
                        reflectiveQuadTo(2.788f, 15.9f)
                        reflectiveQuadTo(2f, 12f)
                        reflectiveQuadToRelative(0.788f, -3.9f)
                        reflectiveQuadToRelative(2.137f, -3.175f)
                        reflectiveQuadTo(8.1f, 2.788f)
                        reflectiveQuadTo(12f, 2f)
                        reflectiveQuadToRelative(3.9f, 0.788f)
                        reflectiveQuadToRelative(3.175f, 2.137f)
                        reflectiveQuadTo(21.213f, 8.1f)
                        reflectiveQuadTo(22f, 12f)
                        reflectiveQuadToRelative(-0.788f, 3.9f)
                        reflectiveQuadToRelative(-2.137f, 3.175f)
                        reflectiveQuadToRelative(-3.175f, 2.138f)
                        reflectiveQuadTo(12f, 22f)
                        moveTo(10.65f, 4.125f)
                        quadToRelative(1.05f, 1.75f, 2.85f, 2.813f)
                        reflectiveQuadTo(17.5f, 8f)
                        quadToRelative(0.35f, 0f, 0.675f, -0.038f)
                        reflectiveQuadToRelative(0.675f, -0.087f)
                        quadTo(17.8f, 6.125f, 16f, 5.063f)
                        reflectiveQuadTo(12f, 4f)
                        quadToRelative(-0.35f, 0f, -0.675f, 0.038f)
                        reflectiveQuadToRelative(-0.675f, 0.087f)
                        moveToRelative(-6.225f, 5.35f)
                        quadTo(5.7f, 8.75f, 6.65f, 7.6f)
                        reflectiveQuadToRelative(1.425f, -2.575f)
                        quadTo(6.8f, 5.75f, 5.85f, 6.9f)
                        reflectiveQuadTo(4.425f, 9.475f)
                        moveToRelative(3.65f, -4.45f)
                    }
                }
                .build()

        return _FaceOutline!!
    }

@Suppress("ObjectPropertyName") private var _FaceOutline: ImageVector? = null
