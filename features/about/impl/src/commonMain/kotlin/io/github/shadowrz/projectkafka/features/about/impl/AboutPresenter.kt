package io.github.shadowrz.projectkafka.features.about.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta

@Inject
class AboutPresenter(
    private val buildMeta: BuildMeta,
) : Presenter<AboutState> {
    @Composable
    override fun present(): AboutState {
        val uriHandler = LocalUriHandler.current

        return AboutState(
            buildMeta = buildMeta,
        ) {
            when (it) {
                AboutEvents.OpenSourceCode -> uriHandler.openUri("https://github.com/ShadowRZ/ProjectKafka")
            }
        }
    }
}
