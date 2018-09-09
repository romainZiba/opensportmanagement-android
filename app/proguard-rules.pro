# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Retrofit 2.X
## https://square.github.io/retrofit/ ##
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions
#
#-keepclasseswithmembers class * {
#    @retrofit2.http.* <methods>;
#}
#
## Okio
#-keep class sun.misc.Unsafe { *; }
#-dontwarn java.nio.file.*
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-dontwarn okio.**
#
## OkHttp
#-keepattributes Signature
#-keepattributes *Annotation*
#-keep class okhttp3.** { *; }
#-keep interface okhttp3.** { *; }
#-dontwarn okhttp3.**
#
## Dagger ProGuard rules.
## https://github.com/square/dagger
#-dontwarn dagger.internal.codegen.**
#-keepclassmembers,allowobfuscation class * {
#    @javax.inject.* *;
#    @dagger.* *;
#    <init>();
#}
#
#-keep class dagger.* { *; }
#-keep class javax.inject.* { *; }
#-keep class * extends dagger.internal.Binding
#-keep class * extends dagger.internal.ModuleAdapter
#-keep class * extends dagger.internal.StaticInjection
#
#
## Proguard configuration for Jackson 2.x (fasterxml package instead of codehaus package)
#-keep class com.fasterxml.jackson.databind.ObjectMapper {
#    public <methods>;
#    protected <methods>;
#}
#-keep class com.fasterxml.jackson.databind.ObjectWriter {
#    public ** writeValueAsString(**);
#}