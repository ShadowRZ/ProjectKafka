package io.github.shadowrz.projectkafka.libraries.data.test

import com.eygraber.uri.Uri
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
class InMemorySystemsStore(
    initialSystems: List<System> = emptyList(),
) : SystemsStore {
    private val systems = MutableStateFlow(initialSystems)

    override fun getSystems(): Flow<List<System>> = systems.asStateFlow()

    override suspend fun getSystem(id: SystemID): System = systems.value.first { it.id == id }

    override suspend fun createSystem(
        name: String,
        description: String?,
        avatar: Uri?,
        cover: Uri?,
    ): SystemID {
        val model =
            System(
                id = SystemID(Uuid.random().toHexString()),
                name = name,
                description = description,
                avatar = avatar,
                cover = cover,
                lastUsed = Instant.fromEpochMilliseconds(0),
            )
        systems.update {
            listOf(*it.toTypedArray(), model)
        }

        return model.id
    }

    override fun lastSystemID(): Flow<SystemID?> =
        systems.map {
            it
                .minByOrNull { system ->
                    system.lastUsed
                }?.id
        }

    override suspend fun updateSystemLastUsed(
        id: SystemID,
        lastUsed: Instant,
    ) {
        systems.update {
            it.map { system ->
                if (id == system.id) {
                    system.copy(
                        lastUsed = lastUsed,
                    )
                } else {
                    system
                }
            }
        }
    }
}
