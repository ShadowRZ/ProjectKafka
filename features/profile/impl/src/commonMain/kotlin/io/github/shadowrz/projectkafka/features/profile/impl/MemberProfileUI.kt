package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.shadowrz.projectkafka.designsystem.Avatar
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.Icon
import io.github.shadowrz.projectkafka.designsystem.KafkaIcons
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.designsystem.OutlinedIconButton
import io.github.shadowrz.projectkafka.designsystem.Scaffold
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TopAppBar
import io.github.shadowrz.projectkafka.designsystem.icons.ChatBubbleOutline
import io.github.shadowrz.projectkafka.designsystem.icons.EditOutline
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.kafkaui.MemberDescription
import io.github.shadowrz.projectkafka.libraries.kafkaui.MemberName
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_edit
import kotlin.math.min
import org.jetbrains.compose.resources.stringResource
import projectkafka.features.profile.impl.generated.resources.Res
import projectkafka.features.profile.impl.generated.resources.profile_no_description

private const val COVER_HEIGHT = 300

@Composable
internal fun MemberProfileUI(
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
                AsyncOutcome.Loading -> {
                    LoadingTopAppBar(onBack = onBack)
                }

                is AsyncOutcome.Success<Member> -> {
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
            AsyncOutcome.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(innerPadding).fillMaxSize().wrapContentSize())
            }

            is AsyncOutcome.Success<Member> -> {
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
            BackButton(onClick = onBack)
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
                    COVER_HEIGHT.dp.toPx()
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
                modifier = Modifier.graphicsLayer { alpha = titleAlpha },
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(
                    modifier = Modifier.size(36.dp),
                    avatar = member.avatar?.value,
                )
                MemberName(
                    member = member,
                    color = KafkaTheme.colors.primary,
                    style = KafkaTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        navigationIcon = {
            BackButton(onClick = onBack)
        },
        containerColor = KafkaTheme.colors.surface.copy(alpha = containerAlpha),
    )
}

@Composable
private fun ColumnScope.Summary(
    member: Member,
    onEdit: () -> Unit = {},
) {
    Box(modifier = Modifier.height(COVER_HEIGHT.dp)) {
        Cover(member.cover?.value, modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter))
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().offset(y = 76.dp).padding(all = 16.dp),
        ) {
            Avatar(
                modifier = Modifier.size(120.dp),
                avatar = member.avatar?.value,
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedIconButton(onClick = {}) {
                Icon(
                    KafkaIcons.ChatBubbleOutline,
                    modifier = Modifier.size(20.dp),
                    contentDescription = null,
                )
            }
            Button(
                text = stringResource(CommonStrings.common_edit),
                leadingIcon = KafkaIcons.EditOutline,
                onClick = onEdit,
            )
        }
    }
    Spacer(modifier = Modifier.height(60.dp))
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        MemberName(
            member = member,
            color = KafkaTheme.colors.primary,
            style = KafkaTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        MemberDescription(
            member = member,
            singleLine = false,
            color = KafkaTheme.colors.onBackground,
            style = KafkaTheme.typography.bodyMedium,
            placeholder = stringResource(Res.string.profile_no_description),
        )
    }
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

@PreviewKafka
@Composable
internal fun PreviewMemberProfileUI(@PreviewParameter(MemberProfileStateProvider::class) state: MemberProfileState) = KafkaPreview {
    MemberProfileUI(state = state)
}
