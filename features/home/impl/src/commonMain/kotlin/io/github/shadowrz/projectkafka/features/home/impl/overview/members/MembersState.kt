package io.github.shadowrz.projectkafka.features.home.impl.overview.members

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Member

@Stable
data class MembersState(
    val members: Result<List<Member>>,
) : HanekokoroState
