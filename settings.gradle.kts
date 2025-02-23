pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

buildCache {
    local {
        isEnabled = true
        directory = "${rootDir}/.build-cache"
    }
}

rootProject.name = "MarketplaceApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:network")
include(":features:search:data")
include(":features:search:domain")
include(":features:search:presentation")
include(":core:domain")
include(":core:ui")
