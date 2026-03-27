package io.github.shadowrz.projectkafka.features.home.impl.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.window.core.layout.WindowSizeClass
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.MediumTopAppBar
import io.github.shadowrz.projectkafka.designsystem.PlainTooltip
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TooltipBox
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_system_subtitle
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.home.impl.generated.resources.Res
import projectkafka.features.home.impl.generated.resources.menu_tooltip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseTopAppBar(
    system: System,
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onAvatarClick: () -> Unit = {},
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val useNavigationRail = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    MediumTopAppBar(
        modifier = modifier,
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                titleContentColor = KafkaTheme.materialColors.primary,
            ),
        title = { Text(title, fontWeight = FontWeight.Bold) },
        subtitle = {
            Text(
                stringResource(
                    CommonStrings.common_system_subtitle,
                    system.name,
                    "",
                ).trim(),
                fontWeight = FontWeight.Light,
            )
        },
        scrollBehavior = scrollBehavior,
        actions = {
            if (!useNavigationRail) {
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
                    tooltip = { PlainTooltip(text = stringResource(Res.string.menu_tooltip)) },
                    state = rememberTooltipState(),
                ) {
                    MenuAvatarButton(
                        avatar = system.avatar,
                        onClick = {
                            onAvatarClick()
                        },
                    )
                }
            }
        },
    )
}
