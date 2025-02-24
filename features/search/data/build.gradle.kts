plugins {
    alias(libs.plugins.marketplace.android.library)
}

android {
    namespace = "io.github.josebatista.marketplace.search.data"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.mercadolibre.com\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.features.search.domain)
}
