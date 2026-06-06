package io.github.shadowrz.projectkafka.designsystem.dialog

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composeunstyled.DialogPanel as UnstyledDialogPanel
import com.composeunstyled.DialogScope

@Composable
fun DialogScope.DialogPanel(
    modifier: Modifier = Modifier,
    enter: EnterTransition = EnterTransition.None,
    exit: ExitTransition = ExitTransition.None,
    content: @Composable () -> Unit,
) =
    CompositionLocalProvider(LocalContentColor provides AlertDialogDefaults.textContentColor) {
        UnstyledDialogPanel(
            modifier =
                modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .widthIn(min = 240.dp, max = 360.dp)
                    .clip(AlertDialogDefaults.shape)
                    .background(AlertDialogDefaults.containerColor)
                    .verticalScroll(rememberScrollState()),
            enter = enter,
            exit = exit,
            content = content,
        )
    }
