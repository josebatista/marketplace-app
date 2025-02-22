package io.github.josebatista.marketplace.buildlogic

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider

fun DependencyHandler.implementation(dependency: Provider<MinimalExternalModuleDependency>) {
    add("implementation", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.testImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    add("testImplementation", dependency)
}
