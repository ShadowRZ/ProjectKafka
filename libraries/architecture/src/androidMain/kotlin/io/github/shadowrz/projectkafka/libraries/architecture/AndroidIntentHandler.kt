package io.github.shadowrz.projectkafka.libraries.architecture

import android.content.Intent

fun interface AndroidIntentHandler {
    fun handleIntent(intent: Intent)
}
