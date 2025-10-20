package io.github.shadowrz.projectkafka.libraries.androidutils

import android.app.Activity
import android.content.ActivityNotFoundException
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsSession
import androidx.core.net.toUri
import io.github.shadowrz.projectkafka.libraries.androidutils.extensions.openUrlInExternalApp

@Suppress("SwallowedException")
fun Activity.openUrlInCustomTab(
    session: CustomTabsSession?,
    url: String,
) {
    try {
        CustomTabsIntent
            .Builder()
            .setShowTitle(true)
            .setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
            .setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
            .setShareIdentityEnabled(false)
            .setBookmarksButtonEnabled(false)
            .setDownloadButtonEnabled(false)
            .apply { session?.let { setSession(it) } }
            .build()
            .launchUrl(this, url.toUri())
    } catch (e: ActivityNotFoundException) {
        openUrlInExternalApp(url)
    }
}
