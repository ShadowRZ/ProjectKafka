package io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters

import app.cash.sqldelight.ColumnAdapter
import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri

object UriAdapter : ColumnAdapter<Uri, String> {
    override fun decode(databaseValue: String): Uri = databaseValue.toKmpUri()

    override fun encode(value: Uri): String = value.toString()
}
