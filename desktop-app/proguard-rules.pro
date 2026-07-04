# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Don't obfuscate class names
-dontobfuscate

# Keep our own SLF4J logger
-keep class io.github.shadowrz.projectkafka.logging.KafkaKermitServiceProvider { *; }

# Library Rules
## Compose Unstyled
# Likely due to we're using a more recent version of CMP
-dontwarn androidx.compose.foundation.ComposeFoundationFlags
## SQLite JDBC
-keep class org.sqlite.** { *; }
-keep class org.sqlite.core.NativeDB { long pointer; }
# Don't warn about SLF4J classes as they're loaded dynamically
-dontwarn org.slf4j.**
## Decompose
-keep class * implements com.arkivanov.decompose.mainthread.MainThreadChecker
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
