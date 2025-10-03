package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.TagDetailLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class TagLocalDataSource internal constructor(
    private val clock: Clock,
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<TagLocalEntity>) {
        database.tag().upsert(entity)
    }

    public suspend fun update(tagId: Uuid, entity: TagDetailLocalEntity) {
        database.tag()
            .update(
                tagId = tagId,
                title = entity.title,
                description = entity.description,
                color = entity.color,
                updatedAt = clock.now().toEpochMilliseconds(),
            )
    }

    public suspend fun updateFinished(
        tagId: Uuid,
        isFinished: Boolean,
    ) {
        database.tag().updateFinished(
            tagId = tagId,
            isFinished = isFinished,
            updatedAt = clock.now().toEpochMilliseconds(),
        )
    }

    public suspend fun updateDeleted(
        tagId: Uuid,
        isDeleted: Boolean,
    ) {
        database.tag().updateDeleted(
            tagId = tagId,
            isDeleted = isDeleted,
            updatedAt = clock.now().toEpochMilliseconds(),
        )
    }

    public fun get(tagId: Uuid): Flow<TagLocalEntity?> {
        return database.tag().get(tagId)
    }
}
