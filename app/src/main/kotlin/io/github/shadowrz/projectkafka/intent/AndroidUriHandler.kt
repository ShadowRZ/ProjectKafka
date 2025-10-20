package io.github.shadowrz.projectkafka.intent

import android.app.Activity
import android.content.Context
import androidx.compose.ui.platform.UriHandler
import io.github.shadowrz.projectkafka.libraries.androidutils.CustomTabUriHandler
import io.github.shadowrz.projectkafka.libraries.androidutils.CustomTabsConnector
import io.github.shadowrz.projectkafka.libraries.androidutils.extensions.openUrlInExternalApp
import io.github.shadowrz.projectkafka.libraries.androidutils.openUrlInCustomTab

class AndroidUriHandler(
    private val context: Context,
    private val customTabsConnector: CustomTabsConnector,
) : UriHandler,
    CustomTabUriHandler {
    override fun openUri(uri: String) {
        context.openUrlInExternalApp(uri)
    }

    override fun openUriInCustomTab(uri: String) {
        customTabsConnector.bindCustomTabService()
        (context as? Activity)?.openUrlInCustomTab(
            session = customTabsConnector.session,
            url = uri,
        ) ?: openUri(uri)
    }
}
