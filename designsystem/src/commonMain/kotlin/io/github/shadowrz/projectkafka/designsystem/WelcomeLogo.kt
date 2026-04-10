package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import projectkafka.designsystem.generated.resources.Res
import projectkafka.designsystem.generated.resources.welcome

@Composable
fun WelcomeLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.welcome),
        modifier = modifier.size(288.dp).padding(32.dp),
        contentDescription = null,
    )
}

@Composable
@PreviewLightDark
internal fun PreviewWelcomeLogo() {
    WelcomeLogo()
}
