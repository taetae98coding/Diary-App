package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.MemoDetailLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class MemoLocalDataSource internal constructor(
    private val clock: Clock,
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(
        entity: Collection<MemoLocalEntity>,
    ) {
        database.memo().upsert(entity)
    }

    public suspend fun update(
        memoId: Uuid,
        entity: MemoDetailLocalEntity,
    ) {
        database.memo()
            .update(
                memoId = memoId,
                title = entity.title,
                description = entity.description,
                start = entity.start,
                endInclusive = entity.endInclusive,
                color = entity.color,
                updatedAt = clock.now().toEpochMilliseconds(),
            )
    }

    public suspend fun updateFinished(
        memoId: Uuid,
        isFinished: Boolean,
    ) {
        database.memo().updateFinished(
            memoId = memoId,
            isFinished = isFinished,
            updatedAt = clock.now().toEpochMilliseconds(),
        )
    }

    public suspend fun updateDeleted(
        memoId: Uuid,
        isDeleted: Boolean,
    ) {
        database.memo().updateDeleted(
            memoId = memoId,
            isDeleted = isDeleted,
            updatedAt = clock.now().toEpochMilliseconds(),
        )
    }

    public suspend fun updatePrimaryTag(memoId: Uuid, tagId: Uuid?) {
        database.memo().updatePrimaryTag(
            memoId = memoId,
            tagId = tagId,
            updatedAt = clock.now().toEpochMilliseconds(),
        )
    }

    public fun get(memoId: Uuid): Flow<MemoLocalEntity?> {
        return database.memo().get(memoId)
    }
}
