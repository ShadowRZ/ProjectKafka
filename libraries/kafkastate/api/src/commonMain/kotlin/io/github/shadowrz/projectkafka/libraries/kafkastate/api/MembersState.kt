package io.github.shadowrz.projectkafka.libraries.kafkastate.api

import androidx.compose.runtime.Stable
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Member

@Stable data class MembersState(val members: AsyncOutcome<List<Member>>) : HanekokoroState
