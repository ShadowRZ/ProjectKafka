package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TwoRowsTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.eygraber.uri.toAndroidUriOrNull
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.libraries.components.Avatar
import io.github.shadowrz.projectkafka.libraries.components.HiddenInTwoPane
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.core.extensions.isNullOrEmpty
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.ArrowBack
import io.github.shadowrz.projectkafka.libraries.icons.material.EditOutline
import io.github.shadowrz.projectkafka.libraries.icons.material.ShieldOutline
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun MemberProfileUI(
    component: MemberProfileComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    MemberProfileUI(
        modifier = modifier,
        state = state,
        onBack = component::navigateUp,
        onEdit = component::onEdit,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
private fun MemberProfileUI(
    state: MemberProfileState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (state.member) {
                Result.Loading -> {
                    LoadingTopAppBar(onBack = onBack)
                }

                is Result.Success<Member> -> {
                    LoadedTopAppBar(
                        member = state.member.value,
                        scrollBehavior = scrollBehavior,
                        onBack = onBack,
                        onEdit = onEdit,
                    )
                }
            }
        },
    ) { innerPadding ->
        Text(
            "Nothing here!",
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .wrapContentSize(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadingTopAppBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            HiddenInTwoPane {
                IconButton(
                    onClick = onBack,
                ) {
                    Icon(
                        MaterialIcons.ArrowBack,
                        contentDescription = stringResource(CommonStrings.common_back),
                    )
                }
            }
        },
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
private fun LoadedTopAppBar(
    member: Member,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
) {
    Box(modifier = modifier) {
        TwoRowsTopAppBar(
            modifier = Modifier.zIndex(2f),
            title = { expanded ->
                if (!expanded) {
                    CollapsedTitle(member = member)
                } else {
                    ExpandedTitle(member = member, onEdit = onEdit)
                }
            },
            expandedHeight = 192.dp,
            colors =
                topAppBarColors(
                    containerColor = if (member.cover.isNullOrEmpty()) {
                        MaterialTheme.colorScheme.background
                    } else {
                        Color.Transparent
                    },
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            navigationIcon = {
                HiddenInTwoPane {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(
                            MaterialIcons.ArrowBack,
                            contentDescription = stringResource(CommonStrings.common_back),
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior,
        )
        AsyncImage(
            member.cover?.toAndroidUriOrNull(),
            modifier = Modifier.matchParentSize().zIndex(1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter,
            contentDescription = null,
        )
    }
}

@Composable
private fun CollapsedTitle(
    member: Member,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            modifier = Modifier.size(36.dp),
            avatar = member.avatar,
        )
        MemberName(member = member)
    }
}

@Composable
private fun ExpandedTitle(
    member: Member,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit = {},
) {
    Row(
        modifier = modifier.padding(end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            modifier = Modifier.size(56.dp),
            avatar = member.avatar,
        )
        Column {
            MemberName(member = member)
            if (member.description.isNullOrEmpty()) {
                Text(
                    stringResource(R.string.profile_no_description),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                )
            } else {
                Text(
                    member.description!!,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onEdit) {
            Icon(
                MaterialIcons.EditOutline,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                stringResource(CommonStrings.common_edit),
            )
        }
    }
}

private const val ADMIN_ID = "adminIcon"

@Composable
private fun MemberName(
    member: Member,
    modifier: Modifier = Modifier,
) {
    val annotatedText = buildAnnotatedString {
        append(member.name)
        if (member.admin) {
            appendInlineContent(ADMIN_ID, "[Admin]")
        }
    }

    Text(
        annotatedText,
        inlineContent = mapOf(
            Pair(
                ADMIN_ID,
                InlineTextContent(
                    placeholder = Placeholder(
                        width = 20.sp,
                        height = 20.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                    ),
                ) {
                    Icon(
                        MaterialIcons.ShieldOutline,
                        modifier = Modifier.fillMaxSize().padding(2.dp),
                        contentDescription = null,
                    )
                },
            ),
        ),
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
    )
}
