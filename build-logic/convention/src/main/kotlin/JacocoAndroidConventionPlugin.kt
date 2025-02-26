import io.github.josebatista.marketplace.buildlogic.Config
import io.github.josebatista.marketplace.buildlogic.applyJaCoCo
import io.github.josebatista.marketplace.buildlogic.capitalized
import io.github.josebatista.marketplace.buildlogic.setDirectories
import io.github.josebatista.marketplace.buildlogic.setupJacocoCoverageReport
import io.github.josebatista.marketplace.buildlogic.setupJacocoCoverageVerification
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyJaCoCo()

            afterEvaluate {
                val module = project.name
                val testCoverageTaskName = "jacoco${module.capitalized()}CoverageReport"
                val verificationTaskName = "jacoco${module.capitalized()}CoverageVerification"

                tasks.register(verificationTaskName, JacocoCoverageVerification::class.java) {
                    setupJacocoCoverageVerification(Config.CURRENT_COVERAGE)
                    dependsOn(testCoverageTaskName)
                    setDirectories(this)
                }

                tasks.register(testCoverageTaskName, JacocoReport::class.java) {
                    setupJacocoCoverageReport()
                    dependsOn("testDebugUnitTest")
                    setDirectories(this)
                }
            }
        }
    }
}
