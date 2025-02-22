plugins {
    alias(libs.plugins.marketplace.android.library)
}

android {
    namespace = "io.github.josebatista.marketplace.data"
}

dependencies {
    implementation(projects.features.search.domain)
    implementation(projects.core.network)
}
