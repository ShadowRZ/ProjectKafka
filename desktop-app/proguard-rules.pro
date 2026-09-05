# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Don't obfuscate class names
-dontobfuscate

# Library Rules

# SLF4J, Logback
-keep class org.slf4j.** { *; }
-keep class ch.qos.logback.** { *; }
-dontwarn ch.qos.logback.**

## Hanekokoro Framework
## TODO: Likely they should belong to a separate artifact
-dontwarn io.github.shadowrz.hanekokoro.framework.runtime.**

## Compose Unstyled
# Likely due to we're using a more recent version of CMP
-dontwarn androidx.compose.foundation.ComposeFoundationFlags

## SQLite JDBC
-keep class org.sqlite.** { *; }
-keep class org.sqlite.core.NativeDB { long pointer; }

## JLine / Jansi
-dontwarn org.jline.terminal.impl.ffm.**

## DataStore Preferences
-keepclassmembernames class androidx.datastore.preferences.PreferencesProto$* { *; }

## Coil
-keep class coil3.util.DecoderServiceLoaderTarget { *; }
-keep class coil3.util.FetcherServiceLoaderTarget { *; }
-keep class coil3.util.ServiceLoaderComponentRegistry { *; }
-keep class * implements coil3.util.DecoderServiceLoaderTarget { *; }
-keep class * implements coil3.util.FetcherServiceLoaderTarget { *; }

## FileKit
# Required on JVM for JNA-based integrations.
-keep class com.sun.jna.** { *; }
-keep class * implements com.sun.jna.** { *; }
# Required when using FileKit Dialogs on Linux (XDG Desktop Portal / DBus).
-keep class org.freedesktop.dbus.** { *; }
-keep class io.github.vinceglb.filekit.dialogs.platform.xdg.** { *; }
-keepattributes Signature,InnerClasses,RuntimeVisibleAnnotations
