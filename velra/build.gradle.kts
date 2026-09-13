plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "io.github.u_nyxx.velra"
    compileSdk = 34
    ndkVersion = "27.0.12077973"

    defaultConfig {
        minSdk = 33
        consumerProguardFiles("consumer-rules.pro")
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    // no third-party — pure framework
}

afterEvaluate {
    publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["release"])
            groupId = "io.github.u-nyxx"
            artifactId = "velra"
            version = "0.1.1"
        }
    }
    }
}
