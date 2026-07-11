package io.github.shadowrz.projectkafka.features.profile.api

import androidx.navigation3.runtime.NavKey
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import kotlinx.serialization.Serializable

@Serializable data class MemberProfileScreen(val memberID: MemberID) : NavKey
