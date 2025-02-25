plugins {
    alias(libs.plugins.marketplace.android.application)
    alias(libs.plugins.marketplace.android.application.compose)
}

android {
    namespace = "io.github.josebatista.marketplace"

    defaultConfig {
        applicationId = "io.github.josebatista.marketplace"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.logging)
    implementation(projects.core.presentation)
    implementation(projects.features.search.data)
    implementation(projects.features.search.domain)
    implementation(projects.features.search.presentation)

    implementation(libs.androidx.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
