
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.compose") version "1.8.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.0"

}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.components.resources)
    implementation(compose.materialIconsExtended)
    implementation(compose.material3)
    implementation("org.xerial:sqlite-jdbc:3.51.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}

compose.desktop {
    application {
        mainClass = "com.windows.splanner.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe)

            packageName = "Splanner"
            packageVersion = "1.0.0"

            // Включаем все модули Java Runtime
            includeAllModules = true

            windows {
                menuGroup = "Splanner"
                shortcut = true
                perUserInstall = true
            }
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.fuse.splanner.resources"
    generateResClass = always
}