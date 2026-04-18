package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.icons.DashboardOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
fun MediumTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    androidx.compose.material3.MediumTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior?.scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
@NonRestartableComposable
fun MediumTopAppBar(
    title: @Composable () -> Unit,
    subtitle: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    androidx.compose.material3.MediumFlexibleTopAppBar(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior?.scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
internal fun PreviewMediumTopAppBar() =
    KafkaPreview {
        Column {
            MediumTopAppBar(
                title = { Text("Title") },
                navigationIcon = { BackButton(onClick = {}) },
                actions = {
                    TextButton("Action", onClick = {})
                    IconButton(onClick = {}) {
                        Icon(
                            KafkaIcons.DashboardOutline,
                            contentDescription = null,
                        )
                    }
                },
            )
            MediumTopAppBar(
                title = { Text("Title") },
                subtitle = { Text("With Subtitle") },
                navigationIcon = { BackButton(onClick = {}) },
                actions = {
                    TextButton("Action", onClick = {})
                    IconButton(onClick = {}) {
                        Icon(
                            KafkaIcons.DashboardOutline,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    }
