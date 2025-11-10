package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.eygraber.uri.Uri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.core.IDGenerator
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.libraries.data.impl.db.toDbModel
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.GlobalDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.Path
import kotlin.time.Instant

@SingleIn(AppScope::class)
@Inject
@ContributesBinding(AppScope::class)
class DefaultSystemsStore(
    private val globalDatabase: GlobalDatabase,
    private val coroutineDispatchers: CoroutineDispatchers,
    @FilesDirectory private val filesDir: Path,
) : SystemsStore {
    override fun getSystems(): Flow<List<System>> =
        globalDatabase.systemQueries
            .systems { id, name, description, avatar, cover, lastUsed ->
                System(
                    id = SystemID(id),
                    name = name,
                    description = description,
                    avatar = avatar?.toAbsolute(filesDir.toString()),
                    cover = cover?.toAbsolute(filesDir.toString()),
                    lastUsed = lastUsed,
                )
            }.asFlow()
            .mapToList(coroutineDispatchers.io)

    override suspend fun getSystem(id: SystemID) =
        globalDatabase.systemQueries
            .systemById(id.value) { id, name, description, avatar, cover, lastUsed ->
                System(
                    id = SystemID(id),
                    name = name,
                    description = description,
                    avatar = avatar?.toAbsolute(filesDir.toString()),
                    cover = cover?.toAbsolute(filesDir.toString()),
                    lastUsed = lastUsed,
                )
            }.executeAsOne()

    override suspend fun createSystem(
        name: String,
        description: String?,
        avatar: Uri?,
        cover: Uri?,
    ): SystemID =
        withContext(coroutineDispatchers.io) {
            val model =
                System(
                    id = SystemID(IDGenerator.generate()),
                    name = name,
                    description = description,
                    avatar = avatar?.toDbRelative(filesDir.toString()),
                    cover = cover?.toDbRelative(filesDir.toString()),
                    lastUsed = Instant.fromEpochMilliseconds(0),
                )
            globalDatabase.systemQueries.insertSystem(model.toDbModel())

            SystemID(globalDatabase.systemQueries.lastSystemID().executeAsOne())
        }

    override fun lastSystemID(): Flow<SystemID?> =
        globalDatabase.systemQueries
            .lastSystemID()
            .asFlow()
            .mapToOneOrNull(coroutineDispatchers.io)
            .map { it?.let { SystemID(it) } }

    override suspend fun updateSystemLastUsed(
        id: SystemID,
        lastUsed: Instant,
    ) {
        withContext(coroutineDispatchers.io) {
            globalDatabase.systemQueries.updateSystemLastUsed(
                id = id.value,
                lastUsed = lastUsed,
            )
        }
    }
}
