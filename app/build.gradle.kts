plugins {
    alias(libs.plugins.marketplace.android.application)
    alias(libs.plugins.marketplace.android.application.compose)
    alias(libs.plugins.detekt)
}

android {
    namespace = "io.github.josebatista.marketplace"

    defaultConfig {
        applicationId = "io.github.josebatista.marketplace"
        versionCode = 1
        versionName = "1.0"
    }
}

detekt {
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    parallel = true
}

dependencies {

    implementation(projects.features.search.data)
    implementation(projects.features.search.domain)
    implementation(projects.features.search.presentation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}