package io.github.shadowrz.projectkafka.features.about.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta

class AboutStateProvider : PreviewParameterProvider<AboutState> {
    override val values: Sequence<AboutState>
        get() = sequenceOf(anAboutState())
}

fun anAboutState(): AboutState =
    AboutState(
        BuildMeta(
            applicationName = "Project Kafka",
            applicationId = "io.github.shadowrz.projectkafka",
            versionName = "1.0",
            versionCode = 1,
            platform = BuildMeta.Platform.Desktop,
        )
    )
