plugins {
    alias(libs.plugins.marketplace.android.library)
}

android {
    namespace = "io.github.josebatista.marketplace.search.domain"
}

dependencies {
    implementation(projects.core.domain)
}
