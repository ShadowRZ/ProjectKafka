# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Repackage classes into the default package to reduce the size of descriptors.
-repackageclasses

# Library Rules

## SQLite JDBC
-keep class org.sqlite.** { *; }
-keep class org.sqlite.core.NativeDB { long pointer; }
# Don't warn about SLF4J classes as they're loaded dynamically
-dontwarn org.slf4j.**
## Decompose
-keep class * implements com.arkivanov.decompose.mainthread.MainThreadChecker
## DataStore Preferences
-keepclassmembernames class androidx.datastore.preferences.PreferencesProto$* { *; }
