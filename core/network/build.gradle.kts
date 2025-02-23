plugins {
    alias(libs.plugins.marketplace.android.library)
}

android {
    namespace = "io.github.josebatista.marketplace.network"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.json)
}
