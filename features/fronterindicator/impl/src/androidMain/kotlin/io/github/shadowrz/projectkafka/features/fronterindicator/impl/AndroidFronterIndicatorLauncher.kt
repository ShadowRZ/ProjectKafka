package io.github.shadowrz.projectkafka.features.fronterindicator.impl

import android.content.Context
import android.content.Intent
import io.github.shadowrz.projectkafka.features.fronterindicator.api.FronterIndicatorLauncher

class AndroidFronterIndicatorLauncher(
    private val context: Context,
) : FronterIndicatorLauncher {
    override fun launch() {
        val intent = Intent(context, FronterIndicatorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
