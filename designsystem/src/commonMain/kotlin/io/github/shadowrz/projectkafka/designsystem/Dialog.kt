package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.composeunstyled.DialogOverlayScope
import com.composeunstyled.DialogScope
import com.composeunstyled.LocalModalState
import com.composeunstyled.buildModifier
import com.composeunstyled.modalFragment

@Composable
fun DialogOverlayScope.Scrim(
    modifier: Modifier = Modifier,
    scrimColor: Color = Color.Black.copy(alpha = 0.6f),
    enter: EnterTransition = EnterTransition.None,
    exit: ExitTransition = ExitTransition.None,
) {
    val state = LocalModalState.current
    AnimatedVisibility(
        visibleState = state.transitionState,
        enter = enter,
        exit = exit,
    ) {
        Box(modifier.modalFragment().fillMaxSize().background(scrimColor))
    }
}

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

@Composable
private fun DialogScope.UnstyledDialogPanel(
    modifier: Modifier = Modifier,
    paneTitle: String? = null,
    enter: EnterTransition = EnterTransition.None,
    exit: ExitTransition = ExitTransition.None,
    content: @Composable () -> Unit,
) {
    val modalState = LocalModalState.current
    val panelFocusRequester = remember { FocusRequester() }

    AnimatedVisibility(
        visibleState = modalState.transitionState,
        enter = enter,
        exit = exit,
    ) {
        LaunchedEffect(Unit) {
            panelFocusRequester.requestFocus()
        }
        Box(
            modifier =
                modifier.modalFragment() then
                    buildModifier {
                        if (paneTitle != null) {
                            add(
                                Modifier.semantics {
                                    this.paneTitle = paneTitle
                                }
                            )
                        }
                        add(Modifier.focusRequester(panelFocusRequester).pointerInput(Unit) { detectTapGestures {} })
                    }
        ) {
            content()
        }
    }
}
