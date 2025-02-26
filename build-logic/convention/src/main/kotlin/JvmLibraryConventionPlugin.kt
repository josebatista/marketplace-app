import io.github.josebatista.marketplace.buildlogic.configureKotlinJvm
import io.github.josebatista.marketplace.buildlogic.libs
import io.github.josebatista.marketplace.buildlogic.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("kotlin.jvm").get().get().pluginId)
            apply(plugin = libs.findPlugin("marketplace.jvm.library.jacoco").get().get().pluginId)
            apply(plugin = libs.findPlugin("marketplace.detekt").get().get().pluginId)
            apply(plugin = libs.findPlugin("marketplace.hilt").get().get().pluginId)
            configureKotlinJvm()
            dependencies {
                testImplementation(libs.findLibrary("google-truth").get())
                testImplementation(libs.findLibrary("kotlin.coroutine.test").get())
                testImplementation(libs.findLibrary("kotlin.test").get())
                testImplementation(libs.findLibrary("mockk").get())
            }
        }
    }
}
