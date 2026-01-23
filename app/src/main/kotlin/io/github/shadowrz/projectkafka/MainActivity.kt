package io.github.shadowrz.projectkafka

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.touchlab.kermit.Logger
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroRoot
import io.github.shadowrz.projectkafka.di.AppBindings
import io.github.shadowrz.projectkafka.intent.AndroidUriHandler
import io.github.shadowrz.projectkafka.libraries.architecture.bindings
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag

class MainActivity : AppCompatActivity() {
    private lateinit var component: MainComponent
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

        splashScreen.setKeepOnScreenCondition { component.shouldShowSplashScreen() }

        setContent {
            CompositionLocalProvider(
                LocalUriHandler provides AndroidUriHandler(this, appBindings.customTabsConnector),
            ) {
                HanekokoroRoot(
                    hanekokoroApp = appBindings.hanekokoroApp,
                ) { context ->
                    MainComponent(
                        context = context,
                        hanekokoroApp = appBindings.hanekokoroApp,
                        plugins =
                            listOf(
                                object : MainComponent.OnInitCallback {
                                    override fun onInit(component: MainComponent) {
                                        Logger.withTag(logger.value).d("onMainComponentInit")
                                        component.handleIntent(intent)
                                    }
                                },
                            ),
                    ).also {
                        splashScreen.setKeepOnScreenCondition {
                            it.shouldShowSplashScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Logger.withTag(logger.value).d("onNewIntent")
        if (::component.isInitialized) {
            component.handleIntent(intent)
        } else {
            setIntent(intent)
        }
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
