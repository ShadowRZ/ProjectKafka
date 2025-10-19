package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.runtime.Stable
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Member

@Stable
data class MemberProfileState(
    val member: Result<Member>,
)
