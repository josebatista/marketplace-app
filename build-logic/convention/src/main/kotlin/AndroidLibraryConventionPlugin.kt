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
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Config.TARGET_SDK
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix =
                    path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
                        .lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
//                "androidTestImplementation"(libs.findLibrary("kotlin.test").get())
//                "testImplementation"(libs.findLibrary("kotlin.test").get())
//                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())

                androidTestImplementation(libs.findLibrary("kotlin.test").get())
                testImplementation(libs.findLibrary("kotlin.test").get())
                implementation(libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}
