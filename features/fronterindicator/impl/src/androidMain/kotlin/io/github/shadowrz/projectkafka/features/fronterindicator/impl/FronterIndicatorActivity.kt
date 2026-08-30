package io.github.shadowrz.projectkafka.features.fronterindicator.impl

import android.app.PictureInPictureParams
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.trackPipAnimationHintView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import kotlinx.coroutines.launch

class FronterIndicatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    trackPipAnimationHintView(this@FronterIndicatorActivity.window.decorView)
                }
            }
            setPictureInPictureParams(PictureInPictureParams.Builder().setAspectRatio(Rational(1, 1)).setAutoEnterEnabled(true).build())
        }

        setContent {
            KafkaTheme {
                FronterIndicator()
            }
        }
    }
}
