package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import io.github.shadowrz.projectkafka.libraries.icons.material.Globe
import io.github.shadowrz.projectkafka.libraries.icons.material.OpenInNew

@Composable
internal actual fun HelpContent(
    onOpenExternalLink: (String) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            stringResource(R.string.helpsheet_help),
            style = MaterialTheme.typography.titleLarge,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                stringResource(R.string.helpsheet_plurality_resources),
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            val colors = ListItemDefaults.colors(
                containerColor = Color.Transparent,
            )

            ListItem(
                colors = colors,
                modifier =
                    Modifier.clickable {
                        onOpenExternalLink("https://wiki.pluralitycn.com")
                    },
                headlineContent = {
                    Text("PluralityCN Wiki")
                },
                supportingContent = {
                    Text("多意识体中文百科")
                },
                leadingContent = {
                    Icon(
                        MaterialIcons.Globe,
                        contentDescription = null,
                    )
                },
                trailingContent = {
                    Icon(
                        MaterialIcons.OpenInNew,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
