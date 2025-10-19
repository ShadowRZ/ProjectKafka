package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.runtime.Stable
import com.mikepenz.aboutlibraries.Libs

@Stable
data class LicensesState(
    val libraries: Libs?,
)
