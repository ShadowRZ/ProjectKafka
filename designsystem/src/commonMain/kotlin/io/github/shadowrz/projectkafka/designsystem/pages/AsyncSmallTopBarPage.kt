package io.github.shadowrz.projectkafka.designsystem.pages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.TopAppBarScrollBehavior
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome

@Composable
fun <T> AsyncSmallTopBarPage(
    state: AsyncOutcome<T>,
    titleStr: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    snackbarHost: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues, T) -> Unit,
) =
    AsyncSmallTopBarPage(
        modifier = modifier,
        state = state,
        topBar = {
            TopAppBar(
                titleStr = titleStr,
                navigationIcon = {
                    if (onBack != null) {
                        BackButton(onClick = onBack)
                    }
                },
                actions = actions,
                windowInsets = windowInsets,
                scrollBehavior = scrollBehavior,
            )
        },
        onBack = onBack,
        snackbarHost = snackbarHost,
        contentWindowInsets = contentWindowInsets,
        content = content,
    )

@Composable
fun <T> AsyncSmallTopBarPage(
    state: AsyncOutcome<T>,
    title: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    snackbarHost: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues, T) -> Unit,
) =
    AsyncSmallTopBarPage(
        modifier = modifier,
        state = state,
        topBar = {
            TopAppBar(
                title = { title(it) },
                navigationIcon = {
                    if (onBack != null) {
                        BackButton(onClick = onBack)
                    }
                },
                actions = actions,
                windowInsets = windowInsets,
                scrollBehavior = scrollBehavior,
            )
        },
        onBack = onBack,
        snackbarHost = snackbarHost,
        contentWindowInsets = contentWindowInsets,
        content = content,
    )

@Composable
fun <T> AsyncSmallTopBarPage(
    state: AsyncOutcome<T>,
    topBar: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    snackbarHost: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues, T) -> Unit,
) =
    Scaffold(
        modifier = modifier,
        topBar = {
            when (state) {
                AsyncOutcome.Loading -> LoadingTopAppBar(onBack = onBack)
                is AsyncOutcome.Success<T> -> topBar(state.value)
            }
        },
        contentWindowInsets = contentWindowInsets,
        content = { innerPadding ->
            when (state) {
                AsyncOutcome.Loading -> LoadingIndicator(modifier = Modifier.padding(innerPadding).fillMaxSize().wrapContentSize())
                is AsyncOutcome.Success<T> -> content(innerPadding, state.value)
            }
        },
        snackbarHost = snackbarHost,
    )

@Composable
private fun LoadingTopAppBar(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            if (onBack != null) {
                BackButton(onClick = onBack)
            }
        },
    )
}
