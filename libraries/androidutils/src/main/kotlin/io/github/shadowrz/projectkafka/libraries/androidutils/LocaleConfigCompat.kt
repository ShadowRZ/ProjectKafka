package io.github.shadowrz.projectkafka.libraries.androidutils

import android.annotation.SuppressLint
import android.app.LocaleConfig
import android.content.Context
import android.content.res.XmlResourceParser
import android.os.Build
import android.util.Log
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import androidx.annotation.XmlRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.LocaleListCompat
import io.github.shadowrz.projectkafka.libraries.androidutils.xml.AndroidManifestParser
import io.github.shadowrz.projectkafka.libraries.androidutils.xml.LocaleConfigParser
import java.io.FileNotFoundException

/**
 * An API-agnostic version of [android.app.LocaleConfig].
 *
 * ## Differences
 *
 * * Only [android.app.LocaleConfig.getSupportedLocales] is provided.
 *   In addition, it returns [LocaleListCompat].
 * * Unlike the platform one, it doesn't implment [android.os.Parcelable].
 *
 * @see android.app.LocaleConfig
 */
@SuppressLint("LogNotTimber")
class LocaleConfigCompat(
    context: Context,
) {
    private var impl: Impl =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Api33Impl(context = context)
        } else {
            Api23Impl(context = context)
        }

    val supportedLocales: LocaleListCompat? by impl::supportedLocales

    companion object {
        /**
         * succeeded reading the LocaleConfig structure stored in an XML file.
         */
        const val STATUS_SUCCESS: Int = 0

        /**
         * No android:localeConfig tag on <application> */
        const val STATUS_NOT_SPECIFIED: Int = 1

        /**
         * Malformed input in the XML file where the LocaleConfig was stored.
         */
        const val STATUS_PARSING_FAILED: Int = 2
    }

    /** @hide */
    @IntDef(
        value = [
            STATUS_SUCCESS,
            STATUS_NOT_SPECIFIED,
            STATUS_PARSING_FAILED,
        ],
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class Status

    private abstract class Impl {
        @get:Status abstract val status: Int
        abstract val supportedLocales: LocaleListCompat?
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private class Api33Impl(
        context: Context,
    ) : Impl() {
        private val inner = LocaleConfig(context)
        override val status: Int by inner::status
        override val supportedLocales: LocaleListCompat?
            get() = inner.supportedLocales?.let { LocaleListCompat.wrap(it) }
    }

    @Suppress("TooGenericExceptionCaught")
    private class Api23Impl(
        context: Context,
    ) : Impl() {
        override var status: Int = STATUS_NOT_SPECIFIED
            private set
        override var supportedLocales: LocaleListCompat? = null
            private set

        init {
            val resourceId =
                try {
                    getLocaleConfigResourceId(context)
                } catch (e: Exception) {
                    Log.w(
                        TAG,
                        "The resource file pointed to by the given resource ID isn't found.",
                        e,
                    )
                    ResourcesCompat.ID_NULL
                }
            if (resourceId == ResourcesCompat.ID_NULL) {
                status = STATUS_NOT_SPECIFIED
            } else {
                val resources = context.resources
                try {
                    supportedLocales = resources.getXml(resourceId).use { parseLocaleConfig(it) }
                    status = STATUS_SUCCESS
                } catch (
                    @Suppress("TooGenericExceptionCaught") e: Exception,
                ) {
                    val resourceEntryName = resources.getResourceEntryName(resourceId)
                    Log.w(TAG, "Failed to parse XML configuration from $resourceEntryName", e)
                    status = STATUS_PARSING_FAILED
                }
            }
        }

        @XmlRes
        private fun getLocaleConfigResourceId(context: Context): Int {
            val parser = findAndroidManifestParser(context)
            return AndroidManifestParser.getLocaleConfigResourceId(parser)
        }

        @Suppress("SwallowedException", "LoopWithTooManyJumpStatements", "KotlinUnreachableCode")
        private fun findAndroidManifestParser(context: Context): XmlResourceParser {
            // Java cookies starts at 1, while passing 0 (invalid cookie for Java) makes
            // AssetManager pick the last asset containing such a file name.
            // We should go over all the assets containing AndroidManifest.xml, however there's no
            // API to do that, so the best we can do is to start from the first asset and iterate
            // until we can't find the next asset containing AndroidManifest.xml.
            var cookie = 1
            while (true) {
                return try {
                    val parser =
                        context.assets.openXmlResourceParser(
                            cookie,
                            FILE_NAME_ANDROID_MANIFEST,
                        )
                    if (!AndroidManifestParser.isOwnAndroidManifest(parser, context.packageName)) {
                        ++cookie
                        continue
                    }
                    return parser
                } catch (
                    _: FileNotFoundException,
                ) {
                    ++cookie
                    continue
                }
            }
        }

        private fun parseLocaleConfig(parser: XmlResourceParser): LocaleListCompat {
            val localeNames = LocaleConfigParser.getLocaleConfig(parser)
            return LocaleListCompat.forLanguageTags(localeNames.joinToString(","))
        }

        companion object {
            private const val TAG = "LocaleConfigCompat"

            private const val FILE_NAME_ANDROID_MANIFEST = "AndroidManifest.xml"
        }
    }
}
