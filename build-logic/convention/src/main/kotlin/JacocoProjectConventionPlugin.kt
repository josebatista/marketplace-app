import io.github.josebatista.marketplace.buildlogic.Config
import io.github.josebatista.marketplace.buildlogic.applyJaCoCo
import io.github.josebatista.marketplace.buildlogic.isJvm
import io.github.josebatista.marketplace.buildlogic.setProjectDirectories
import io.github.josebatista.marketplace.buildlogic.setupJacocoCoverageReport
import io.github.josebatista.marketplace.buildlogic.setupJacocoCoverageVerification
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoProjectConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyJaCoCo()
            afterEvaluate {
                tasks.register("jacocoProjectCoverageReport", JacocoReport::class.java) {
                    setupJacocoCoverageReport()
                    subprojects.forEach { subproject ->
                        if (isJvm()) {
                            dependsOn(subproject.tasks.matching { it.name == "test" })
                        } else {
                            dependsOn(subproject.tasks.matching { it.name == "testDebugUnitTest" })
                        }
                    }
                    setProjectDirectories(this)
                }
                tasks.register(
                    "jacocoProjectCoverageVerification",
                    JacocoCoverageVerification::class.java
                ) {
                    setupJacocoCoverageVerification(Config.CURRENT_COVERAGE)
                    dependsOn("jacocoProjectCoverageReport")
                    setProjectDirectories(this)
                }
            }
        }
    }
}
