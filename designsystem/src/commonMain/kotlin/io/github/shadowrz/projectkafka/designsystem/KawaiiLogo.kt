package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.assets.SharedDrawables
import io.github.shadowrz.projectkafka.assets.kawaii_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun KawaiiLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(SharedDrawables.kawaii_logo),
        modifier = modifier,
        contentDescription = null,
    )
}

@Composable
@PreviewLightDark
internal fun PreviewKawaiiLogo() {
    Surface(modifier = Modifier.size(400.dp)) {
        KawaiiLogo(modifier = Modifier.fillMaxSize())
    }
}
