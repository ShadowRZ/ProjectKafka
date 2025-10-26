package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.content.Context
import java.io.File
import java.nio.file.Files

fun Context.createTempDirectory(prefix: String): File = Files.createTempDirectory(cacheDir.toPath(), prefix).toFile()
