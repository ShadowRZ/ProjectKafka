package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Developer
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.Scm

class LicensesStateProvider : PreviewParameterProvider<LicensesState> {
    override val values: Sequence<LicensesState>
        get() =
            sequenceOf(
                aLicensesState(),
                aLicensesState(
                    libraries =
                        Libs(
                            libraries =
                                listOf(
                                    Library(
                                        uniqueId = "io.github.shadowrz.projectkafka",
                                        artifactVersion = "1.0",
                                        name = "Project Kafka",
                                        description = "Project Kafka",
                                        website = "https://github.com/ShadowRZ/ProjectKafka",
                                        developers = listOf(Developer(name = "Yorusaka Miyabi", organisationUrl = null)),
                                        organization = null,
                                        scm =
                                            Scm(
                                                connection = null,
                                                developerConnection = null,
                                                url = "https://github.com/ShadowRZ/ProjectKafka",
                                            ),
                                    )
                                ),
                            licenses = emptySet(),
                        )
                ),
            )
}

fun aLicensesState(libraries: Libs? = null) = LicensesState(libraries = libraries)
