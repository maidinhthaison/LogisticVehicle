// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //kotlin("jvm") version "2.0.0" // Your Kotlin version
    // id ("org.jetbrains.kotlin.kapt") version "1.8.10" apply false
    // Migrate kapt to ksp https://developer.android.com/build/migrate-to-ksp
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id ("com.google.dagger.hilt.android") version "2.48" apply false

}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // ... other buildscript dependencies
        // Safe Args plugin for Navigation Component
        classpath(libs.navigation.safe.args.gradle.plugin) // Use the latest stable version
    }
}
