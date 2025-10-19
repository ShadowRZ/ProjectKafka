package io.github.shadowrz.projectkafka.libraries.androidutils.xml

import org.xmlpull.v1.XmlPullParser

object LocaleConfigParser {
    fun getLocaleConfig(parser: XmlPullParser): Set<String> {
        val localeNames = mutableSetOf<String>()

        while (
            parser.eventType != XmlPullParser.END_DOCUMENT &&
            parser.eventType != XmlPullParser.START_TAG &&
            parser.name != TAG_LOCALE_CONFIG
        ) {
            parser.next()
        }

        val outerDepth = parser.depth

        while (nextElementWithin(parser, outerDepth)) {
            if (parser.name == TAG_LOCALE) {
                localeNames +=
                    parser.getAttributeValue(
                        NAMESPACE_ANDROID,
                        ATTR_NAME,
                    )
            } else {
                skipCurrentTag(parser)
            }
        }

        return localeNames
    }

    private fun skipCurrentTag(parser: XmlPullParser) {
        val outerDepth = parser.depth
        var type = parser.next()
        while (type != XmlPullParser.END_DOCUMENT &&
            (type == XmlPullParser.END_TAG || parser.depth > outerDepth)
        ) {
            type = parser.next()
        }
    }

    private fun nextElementWithin(
        parser: XmlPullParser,
        outerDepth: Int,
    ): Boolean {
        while (true) {
            val type = parser.next()
            if (type == XmlPullParser.END_DOCUMENT ||
                (type == XmlPullParser.END_TAG && parser.depth == outerDepth)
            ) {
                return false
            }
            if (type == XmlPullParser.START_TAG &&
                parser.depth == outerDepth + 1
            ) {
                return true
            }
        }
    }

    private const val TAG_LOCALE_CONFIG = "locale-config"
    private const val TAG_LOCALE = "locale"
    private const val ATTR_NAME = "name"

    private const val NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
}
