package io.github.shadowrz.projectkafka

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import io.github.shadowrz.projectkafka.di.AppBindings
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.architecture.bindings
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.navigation.ProvideComponentUIFactories
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var component: MainComponent
    private lateinit var appBindings: AppBindings
    private lateinit var uiFactories: ComponentUI.Factories
    private val logger = LoggerTag("MainActivity", LoggerTag.Root)

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(logger.value).d("onCreate, has savedInstanceState? ${savedInstanceState != null}")
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        appBindings = bindings()
        uiFactories = bindings()

        component =
            MainComponent(
                defaultComponentContext(),
                plugins =
                    listOf(
                        object : MainComponent.OnInitCallback {
                            override fun onInit(component: MainComponent) {
                                Timber.tag(logger.value).d("onMainComponentInit")
                                component.handleIntent(intent)
                            }
                        },
                    ),
                context = applicationContext,
            )

        splashScreen.setKeepOnScreenCondition { component.shouldShowSplashScreen() }

        setContent {
            ProvideComponentUIFactories(uiFactories) {
                MainUI(
                    component = component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.tag(logger.value).d("onNewIntent")
        if (::component.isInitialized) {
            component.handleIntent(intent)
        } else {
            setIntent(intent)
        }
    }
}
