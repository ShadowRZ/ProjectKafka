package io.github.shadowrz.projectkafka.libraries.androidutils

import android.content.ComponentName
import android.content.Context
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession

class CustomTabsConnector(
    private val context: Context,
) {
    private var _client: CustomTabsClient? = null
    private var _session: CustomTabsSession? = null

    val client get() = _client
    val session get() = _session

    private val connection: CustomTabsServiceConnection =
        object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(
                name: ComponentName,
                client: CustomTabsClient,
            ) {
                _client = client
                _client!!.warmup(0)
                _session = _client!!.newSession(CustomTabsCallback())
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                _client = null
                _session = null
            }
        }

    fun bindCustomTabService() {
        // Check for an existing connection
        if (_client != null) {
            // Do nothing if there is an existing service connection
            return
        }

        // Get the default browser package name, this will be null if
        // the default browser does not provide a CustomTabsService
        val packageName = CustomTabsClient.getPackageName(context, null)
        if (packageName == null) {
            // Do nothing as service connection is not supported
            return
        }
        CustomTabsClient.bindCustomTabsService(context, packageName, connection)
    }
}
