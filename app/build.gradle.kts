
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.compose") version "1.8.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

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
