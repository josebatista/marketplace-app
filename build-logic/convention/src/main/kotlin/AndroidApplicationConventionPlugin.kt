import com.android.build.api.dsl.ApplicationExtension
import io.github.josebatista.marketplace.buildlogic.Config
import io.github.josebatista.marketplace.buildlogic.configureKotlinAndroid
import io.github.josebatista.marketplace.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("android.application").get().get().pluginId)
            apply(plugin = libs.findPlugin("kotlin.android").get().get().pluginId)
            apply(plugin = libs.findPlugin("marketplace-hilt").get().get().pluginId)
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Config.TARGET_SDK
                @Suppress("UnstableApiUsage")
                testOptions.animationsDisabled = true
            }
        }
    }
}
