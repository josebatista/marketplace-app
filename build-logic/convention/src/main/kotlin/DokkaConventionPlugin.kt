import io.github.josebatista.marketplace.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial

class DokkaConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("dokka").get().get().pluginId)
            tasks.withType(DokkaTaskPartial::class.java).configureEach {
                dokkaSourceSets.configureEach {
                    val moduleNameValue = project.path.removePrefix(":")
                    moduleName.set(moduleNameValue)
                    reportUndocumented.set(true)
                    if (file("README.md").exists()) {
                        includes.from("README.md")
                    }
                    documentedVisibilities.set(
                        setOf(
                            DokkaConfiguration.Visibility.PUBLIC,
                            DokkaConfiguration.Visibility.INTERNAL,
                            DokkaConfiguration.Visibility.PROTECTED,
                            DokkaConfiguration.Visibility.PRIVATE
                        )
                    )
                }
                outputDirectory.set(layout.buildDirectory.dir("docs/partial"))
            }
        }
    }
}
