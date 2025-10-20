package io.github.shadowrz.projectkafka.libraries.androidutils.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.net.toUri
import io.github.shadowrz.projectkafka.libraries.androidutils.R

@Suppress("SwallowedException")
fun Context.openUrlInExternalApp(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    if (this !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        toast(R.string.error_no_compatible_apps_found)
    }
}

fun Context.toast(
    @StringRes resId: Int,
) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
