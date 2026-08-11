plugins {
    alias(libs.plugins.projectkafka)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.multiplatform) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.hotreload) apply false
    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kotest) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.stability.analyzer) apply false
}

kover {
    reports {
        filters {
            excludes {
                androidGeneratedClasses()
                classes(
                    "*ComposableSingletons$*",
                    // Metro
                    $$"*$MetroContributionTo*",
                    $$"*$MetroFactory*",
                    // State Providers
                    "*StateProvider",
                    "*StateProviderKt",
                    // We are currently not in position of testing NavEntryProviders.
                    "*NavEntryProvider",
                )
                annotatedBy(
                    // Origin annotations
                    "dev.zacsweers.metro.Origin",
                    // Preview
                    "androidx.compose.ui.tooling.preview.Preview",
                )
                packages(
                    "io.github.shadowrz.projectkafka.designsystem.preview.*",
                    "io.github.shadowrz.projectkafka.libraries.data.impl.db",
                    "io.github.shadowrz.projectkafka.libraries.data.impl.db.impl",
                    "projectkafka.*.generated.resources",
                )
                inheritedFrom(
                    // Screens are only data classes which attachs no test value
                    "androidx.navigation3.runtime.NavKey",
                    // For Previews only
                    "androidx.compose.ui.tooling.preview.PreviewParameterProvider",
                )
            }
        }
    }
}
