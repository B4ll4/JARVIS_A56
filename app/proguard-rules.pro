# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-verbose

# Shizuku
-keep class dev.rikka.shizuku.** { *; }
-keepclassmembers class dev.rikka.shizuku.** { *; }

# Samsung Knox
-keep class com.samsung.android.knox.** { *; }
-keepclassmembers class com.samsung.android.knox.** { *; }

# Jetpack Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }

# Kotlin
-keep class kotlin.** { *; }
-keepclassmembers class kotlin.** { *; }

# Timber
-keep class timber.log.Timber { *; }
-keepclassmembers class timber.log.Timber { *; }

# GSON
-keep class com.google.gson.** { *; }
-keepclassmembers class com.google.gson.** { *; }

# Coroutines
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }

# Keep your app's classes
-keep class com.jarvis.a56.** { *; }
-keepclassmembers class com.jarvis.a56.** { *; }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable implementations
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
