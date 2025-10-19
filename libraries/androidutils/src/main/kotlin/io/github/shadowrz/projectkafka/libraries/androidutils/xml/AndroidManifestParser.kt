package io.github.shadowrz.projectkafka.libraries.androidutils.xml

import android.content.res.XmlResourceParser
import androidx.annotation.XmlRes
import androidx.core.content.res.ResourcesCompat
import org.xmlpull.v1.XmlPullParser

internal object AndroidManifestParser {
    @XmlRes
    fun getLocaleConfigResourceId(parser: XmlResourceParser): Int {
        parser.use {
            // Assumes that we already checked we are in our own package manifest
            while (
                it.eventType != XmlPullParser.END_DOCUMENT &&
                (
                    it.eventType != XmlPullParser.START_TAG ||
                        it.name != TAG_APPLICATION
                )
            ) {
                it.next()
            }

            return it.getAttributeResourceValue(
                NAMESPACE_ANDROID,
                ATTR_LOCALE_CONFIG,
                ResourcesCompat.ID_NULL,
            )
        }
    }

    fun isOwnAndroidManifest(
        parser: XmlResourceParser,
        packageName: String,
    ): Boolean {
        parser.let {
            // <manifest>...</manifest>
            while (
                it.eventType != XmlPullParser.END_DOCUMENT &&
                it.eventType != XmlPullParser.START_TAG &&
                it.name != TAG_MANIFEST
            ) {
                it.next()
            }
            // This is not our package
            if (it.getAttributeValue(null, ATTR_PACKAGE) != packageName) {
                return false
            }
        }

        return true
    }

    private const val TAG_APPLICATION = "application"
    private const val TAG_MANIFEST = "manifest"

    private const val ATTR_LOCALE_CONFIG = "localeConfig"
    private const val ATTR_PACKAGE = "package"

    private const val NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
}
