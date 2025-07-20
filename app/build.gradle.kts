import java.io.PrintWriter

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Apply the Kapt plugin
    /* id ("kotlin-kapt")
     id("org.jetbrains.kotlin.kapt")*/
    // Migrate kapt to ksp https://developer.android.com/build/migrate-to-ksp
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")   // dependencies injection, inject classes generator
    id ("androidx.navigation.safeargs.kotlin") // This is the Safe Args plugin for Kotlin
}

android {
    namespace = "com.linkon"
    compileSdk = 35
    buildToolsVersion = "34.0.0"
    defaultConfig {
        applicationId = "com.linkon"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }

        signingConfigs {
            create("release") {
                storeFile = file("release_keystore.jks")
                storePassword = ""
                keyAlias = "alias"
                keyPassword = ""
            }

        }

    }

    buildTypes {
        getByName("debug"){
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        getByName("release"){
            isMinifyEnabled = false
            //isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    /**
     * flavor
     */
    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }

        create("prod") {
            dimension = "environment"
        }
    }
    packaging {
        resources {
            excludes += "META-INF/*"
        }
    }

    applicationVariants.all {
        when (name) {
            "devDebug", "devRelease" -> {
                buildConfigField("String", "BASE_API_URL",
                    "\"http://47.81.15.63:8090/\"")
            }
            "prodDebug", "prodRelease" -> {
                buildConfigField("String", "BASE_API_URL",
                    "\"http://47.81.15.63:8090/\"")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DI
    implementation(libs.com.google.dagger.hilt.android)
    ksp(libs.com.google.dagger.hilt.compiler)

    // reactive programming
    implementation(libs.io.reactivex.rxjava2.rxandroid)
    implementation(libs.io.reactivex.rxjava2.rxjava)

    // Restful api client
    implementation(libs.com.squareup.okhttp3)
    implementation(libs.com.squareup.retrofit2)
    implementation(libs.com.squareup.retrofit2.converter.gson)

    //Glide
    implementation(libs.com.github.bumptech.glide)
    ksp(libs.com.github.bumptech.glide.compiler)

    //Logging
    implementation(libs.com.jakewharton.timber)

    //Unit test
    testImplementation(libs.io.mockk)
    androidTestImplementation(libs.io.mockk.android)
    testImplementation(libs.org.jetbrains.kotlinx)
    testImplementation(libs.androidx.arch.core)

    // multi dex
    implementation(libs.androidx.multidex)

    // preference
    implementation(libs.androidx.preference)
    //encrypt shared preference
    implementation(libs.androidx.security)

    implementation(libs.androidx.swiperefreshlayout)

    // Google Maps SDK, Location
    implementation(libs.com.google.android.gms)
    implementation(libs.com.google.android.gms.location)
}

open class SdpFactory : DefaultTask() {

    // Use '@get:Input' for task properties in Kotlin.
    // 'var' is used to make them configurable.
    @get:Input
    var unitSp: String = "sp"

    @get:Input
    var positiveMaxSp: Int = 128

    @get:Input
    var unitDp: String = "dp"

    @get:Input
    var positiveMaxDp: Int = 512

    @TaskAction
    fun create() {
        // Get the project's res folder path safely.
        val resFolder = "${project.projectDir.path}/src/main/res/"

        // Loop from 300 to 1080 with a step of 30.
        for (dimen in 300..1080 step 30) {
            val folderPath = "${resFolder}values-sw${dimen}dp"
            val folder = File(folderPath)
            // Create the directory if it doesn't exist.
            if (!folder.exists()) {
                folder.mkdirs()
            }

            val fileName = "$folderPath/dimens.xml"
            val dimenFile = File(fileName)

            // Use printWriter() with .use to automatically handle closing the writer.
            dimenFile.printWriter().use { writer ->
                writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                writer.println("<resources>")

                // Loop from 1 up to the positiveMax value.
                for (i in 1..positiveMaxSp) {
                    val ratio = i / 360.0
                    val sdp = ratio * dimen
                    // Use String.format for clean, formatted output.
                    writer.println(
                        String.format(
                            "\t<dimen name=\"_%dsp\">%.2f%s</dimen>",
                            i,
                            sdp,
                            unitSp
                        )
                    )
                }
                for (i in 1..positiveMaxDp) {
                    val ratio = i / 360.0
                    val sdp = ratio * dimen
                    // Use String.format for clean, formatted output.
                    writer.println(
                        String.format(
                            "\t<dimen name=\"_%ddp\">%.2f%s</dimen>",
                            i,
                            sdp,
                            unitDp
                        )
                    )

                }
                writer.println("</resources>")
            }
        }
    }
}

// Register the task with the specified type (SdpFactory).
private val createSdp = tasks.register<SdpFactory>("createSDP") {
    // Configure default values directly within the registration block.
    // These can be overridden later.
    group = "generation"
    description = "Generates SDP dimension files for different screen widths."

    unitSp = "sp" // change to "dp" if needed
    positiveMaxSp = 128 // change to 600 or any other value if needed

    unitDp = "dp" // change to "dp" if needed
    positiveMaxDp = 512 // change to 600 or any other value if needed
}

tasks.named("clean") {
    dependsOn(createSdp)
}
// Set up dependencies. This is the modern, safe way to configure task dependencies.

// To use this task before a build, you would uncomment the following lines.
// It's commented out by default to prevent it from running automatically.
/*
tasks.named("preBuild") {
    dependsOn(createSdp)
}
*/
