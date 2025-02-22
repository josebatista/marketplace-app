import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import io.github.josebatista.marketplace.buildlogic.Config
import io.github.josebatista.marketplace.buildlogic.androidTestImplementation
import io.github.josebatista.marketplace.buildlogic.configureKotlinAndroid
import io.github.josebatista.marketplace.buildlogic.disableUnnecessaryAndroidTests
import io.github.josebatista.marketplace.buildlogic.implementation
import io.github.josebatista.marketplace.buildlogic.libs
import io.github.josebatista.marketplace.buildlogic.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("android.library").get().get().pluginId)
            apply(plugin = libs.findPlugin("kotlin.android").get().get().pluginId)
            apply(plugin = libs.findPlugin("kotlin.serialization").get().get().pluginId)
            apply(plugin = libs.findPlugin("detekt").get().get().pluginId)
            apply(plugin = libs.findPlugin("marketplace-hilt").get().get().pluginId)
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Config.TARGET_SDK
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                resourcePrefix =
                    path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
                        .lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                implementation(libs.findLibrary("androidx.core.ktx").get())
                implementation(libs.findLibrary("kotlin.serialization.json").get())

                testImplementation(libs.findLibrary("google-truth").get())
                testImplementation(libs.findLibrary("kotlin.coroutine.test").get())
                testImplementation(libs.findLibrary("mockk.android").get())
                testImplementation(libs.findLibrary("mockk.android.agent").get())
                testImplementation(libs.findLibrary("junit").get())
                androidTestImplementation(libs.findLibrary("androidx.junit").get())
            }
        }
    }
}
