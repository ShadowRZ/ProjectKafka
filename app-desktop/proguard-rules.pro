# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Repackage classes into the default package to reduce the size of descriptors.
-repackageclasses

# Don't warn about SLF4J classes
-dontwarn org.slf4j.**

# Library Rules

## SQLite JDBC
-keep class org.sqlite.** { *; }
-keep class org.sqlite.core.NativeDB { long pointer; }
## Decompose
-keep class * implements com.arkivanov.decompose.mainthread.MainThreadChecker
