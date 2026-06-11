package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.adaptive.HiddenInTwoPane
import io.github.shadowrz.projectkafka.designsystem.icons.EditOutline
import io.github.shadowrz.projectkafka.designsystem.icons.ShieldOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_edit
import kotlin.math.min
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.profile.impl.generated.resources.Res
import projectkafka.features.profile.impl.generated.resources.profile_no_description

private const val COVER_HEIGHT = 300

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

@Composable
private fun MemberProfileUI(
    state: MemberProfileState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
) {

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            when (state.member) {
                Result.Loading -> {
                    LoadingTopAppBar(onBack = onBack)
                }

                is Result.Success<Member> -> {
                    LoadedTopAppBar(
                        member = state.member.value,
                        scrollState = scrollState,
                        onBack = onBack,
                    )
                }
            }
        },
    ) { innerPadding ->
        when (state.member) {
            Result.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(innerPadding).fillMaxSize().wrapContentSize())
            }

            is Result.Success<Member> -> {
                Column(
                    modifier = Modifier.consumeWindowInsets(innerPadding).fillMaxSize().verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Summary(member = state.member.value, onEdit = onEdit)
                    Content(member = state.member.value)
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.Content(member: Member) {
    Text("Nothing here!".repeat(300), modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize().wrapContentSize())
}

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
                BackButton(onClick = onBack)
            }
        },
    )
}

@Composable
private fun LoadedTopAppBar(
    member: Member,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    val density = LocalDensity.current
    val factor by remember {
        derivedStateOf {
            scrollState.value /
                with(density) {
                    (COVER_HEIGHT - 96).dp.toPx()
                }
        }
    }

    val showTitle by remember { derivedStateOf { factor >= 1.0f } }
    val titleAlpha by animateFloatAsState(if (showTitle) 1.0f else 0.0f)
    val containerAlpha = min(factor, 1.0f)

    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                modifier = modifier.graphicsLayer { alpha = titleAlpha },
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(
                    modifier = Modifier.size(36.dp),
                    avatar = member.avatar?.value,
                )
                MemberName(member = member)
            }
        },
        navigationIcon = {
            HiddenInTwoPane {
                BackButton(onClick = onBack)
            }
        },
        containerColor = KafkaTheme.materialColors.surface.copy(alpha = containerAlpha),
    )
}

@Composable
private fun Summary(
    member: Member,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit = {},
) {
    Box(modifier = modifier.height(COVER_HEIGHT.dp)) {
        Cover(member.cover?.value, modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
        ) {
            Avatar(
                modifier = Modifier.size(56.dp),
                avatar = member.avatar?.value,
            )
            Column(modifier = Modifier.weight(1f)) {
                MemberName(member = member)
                if (member.description.isNullOrEmpty()) {
                    Text(
                        stringResource(Res.string.profile_no_description),
                        color = KafkaTheme.materialColors.onBackground.copy(alpha = 0.5f),
                        style = KafkaTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                } else {
                    Text(
                        member.description!!,
                        color = KafkaTheme.materialColors.onBackground,
                        style = KafkaTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
            Button(
                text = stringResource(CommonStrings.common_edit),
                leadingIcon = KafkaIcons.EditOutline,
                onClick = onEdit,
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
        inlineContent =
            mapOf(
                Pair(
                    ADMIN_ID,
                    InlineTextContent(
                        placeholder =
                            Placeholder(
                                width = 20.sp,
                                height = 20.sp,
                                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                            )
                    ) {
                        Icon(
                            KafkaIcons.ShieldOutline,
                            modifier = Modifier.fillMaxSize().padding(2.dp),
                            contentDescription = null,
                            tint = KafkaTheme.materialColors.primary,
                        )
                    },
                )
            ),
        modifier = modifier,
        color = KafkaTheme.materialColors.primary,
        style = KafkaTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun Cover(
    cover: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = cover,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
internal fun PreviewMemberProfileUI(@PreviewParameter(MemberProfileStateProvider::class) state: MemberProfileState) = KafkaPreview {
    MemberProfileUI(state = state)
}
