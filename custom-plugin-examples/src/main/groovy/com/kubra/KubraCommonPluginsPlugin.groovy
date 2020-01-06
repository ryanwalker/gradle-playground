package com.kubra

import org.gradle.api.Plugin
import org.gradle.api.Project

class KubraCommonPluginsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        applyJacocoPlugin(project)
        applySonarqubePlugin(project)
        applySpotlessJavaPlugin(project)
    }

    private void applyJacocoPlugin(Project project) {
        project.plugins.apply('jacoco')

        def jacocoExtension = project.extensions.getByName('jacoco')
        jacocoExtension.with {
            toolVersion = "0.8.4"
        }

        def jacocoTestReport = project.getTasks().getByName('jacocoTestReport')
        jacocoTestReport.with {
            reports { xml.enabled true }
        }

    }

    private void applySonarqubePlugin(Project project) {
        project.plugins.apply('org.sonarqube')

        def sonarExtension = project.extensions.getByName("sonarqube")
        sonarExtension.with {
            properties {
                property "sonar.projectKey", "$project.name"
                property "sonar.projectName", "$project.name"
                property "sonar.host.url", "https://sonarcloud.io"
                property "sonar.organization", "kubra"
                property "sonar.language", "java"
                property "sonar.sources", "src/main/java"
                property "sonar.java.binaries", "build/classes"
                property "sonar.java.libraries", "$System.env.HOME/.gradle/caches/modules-2/files-2.1/**"
                property "sonar.exclusions", "src/main/java/com/kubra/prepay/generated/**,src/main/java/com/kubra/prepay/integration/generated/**"
                property "sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml"
            }
        }

    }

    private void applySpotlessJavaPlugin(Project project) {
        project.plugins.apply("com.diffplug.gradle.spotless")

        def spotlessExtension = project.extensions.getByName('spotless')
        spotlessExtension.with {
            java {
                target project.fileTree(project.rootDir) {
                    exclude '**/generated/**'
                    include 'src/**/*.java'
                }
                googleJavaFormat()
                paddedCell()
            }

            groovyGradle {
                // same as groovy, but for .gradle (defaults to '*.gradle')
                target '*.gradle'
                // TODO - get this property file working
//                greclipse().configFile('src/main/resources/gradle-format.properties')
                paddedCell()
            }

        }

        // Run spotlessApply every time java compiles
        project.getTasks().getByName('compileJava') dependsOn 'spotlessApply'
    }


}