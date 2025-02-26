import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "io.github.josebatista.marketplace.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = libs.plugins.marketplace.android.application.compose.get().pluginId
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.marketplace.android.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.marketplace.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.marketplace.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.marketplace.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.marketplace.jvm.library.asProvider().get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("detekt") {
            id = libs.plugins.marketplace.detekt.get().pluginId
            implementationClass = "DetektConventionPlugin"
        }
        register("jacocoProjectConventionPlugin") {
            id = libs.plugins.marketplace.project.library.jacoco.get().pluginId
            implementationClass = "JacocoProjectConventionPlugin"
        }
        register("jacocoAndroidConventionPlugin") {
            id = libs.plugins.marketplace.android.jacoco.get().pluginId
            implementationClass = "JacocoAndroidConventionPlugin"
        }
        register("jacocoJvmConventionPlugin") {
            id = libs.plugins.marketplace.jvm.library.jacoco.get().pluginId
            implementationClass = "JacocoJvmConventionPlugin"
        }
    }
}
