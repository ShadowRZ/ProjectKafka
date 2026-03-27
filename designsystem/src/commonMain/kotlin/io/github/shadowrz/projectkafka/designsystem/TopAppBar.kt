package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.icons.DashboardOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
fun TopAppBar(
    titleStr: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    androidx.compose.material3.TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = titleStr,
                modifier = Modifier.semantics { heading() },
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@NonRestartableComposable
fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    androidx.compose.material3.TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
internal fun PreviewTopAppBar() =
    KafkaPreview {
        Column {
            TopAppBar(
                titleStr = "Title String",
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
            TopAppBar(
                title = { Text("Title Composable") },
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
