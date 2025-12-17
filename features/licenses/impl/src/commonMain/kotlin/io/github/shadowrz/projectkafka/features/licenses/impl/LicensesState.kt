package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.runtime.Stable
import com.mikepenz.aboutlibraries.Libs
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

@Stable
data class LicensesState(
    val libraries: Libs?,
) : HanekokoroState
