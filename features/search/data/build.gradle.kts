plugins {
    alias(libs.plugins.marketplace.android.library)
}

android {
    namespace = "io.github.josebatista.marketplace.search.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.logging)
    implementation(projects.features.search.domain)
}
