package io.github.shadowrz.projectkafka.libraries.crop

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
internal actual fun useVerticalToolbar(): Boolean = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
