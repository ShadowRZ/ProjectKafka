package io.github.shadowrz.projectkafka

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.touchlab.kermit.Logger
import io.github.shadowrz.projectkafka.di.AppBindings
import io.github.shadowrz.projectkafka.libraries.architecture.bindings
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag

class MainActivity : ComponentActivity() {
    private lateinit var appBindings: AppBindings
    private val logger = LoggerTag("MainActivity", LoggerTag.Root)

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.withTag(logger.value).d { "onCreate, has savedInstanceState? ${savedInstanceState != null}" }
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        appBindings = bindings()

        var shouldShowSplashScreen = true

        splashScreen.setKeepOnScreenCondition { shouldShowSplashScreen }

        setContent {
            appBindings.kafkaApp.Content(showSplashScreen = { shouldShowSplashScreen = false })
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Logger.withTag(logger.value).d("onNewIntent")
    }

    override fun onPause() {
        Logger.withTag(logger.value).d("onDestroy")
        super.onPause()
    }

    override fun onResume() {
        Logger.withTag(logger.value).d("onResume")
        super.onResume()
    }

    override fun onStop() {
        Logger.withTag(logger.value).d("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Logger.withTag(logger.value).d("onDestroy")
        super.onDestroy()
    }
}
