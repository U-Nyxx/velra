plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.github.u_nyxx.velra.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.u_nyxx.velra.sample"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.1"
    }
}

dependencies {
    implementation(project(":velra"))
    implementation(platform("androidx.compose:compose-bom:2024.08.00"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.core:core-ktx:1.13.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}