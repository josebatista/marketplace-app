plugins {
    alias(libs.plugins.marketplace.android.library)
    alias(libs.plugins.marketplace.android.library.compose)
}

android {
    namespace = "io.github.josebatista.marketplace.search.presentation"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.domain)

    implementation(projects.features.search.data)
    implementation(projects.features.search.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
