package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class MemoTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<MemoTagLocalEntity>) {
        database.memoTag().upsert(entity)
    }

    public suspend fun delete(memoId: Uuid) {
        database.memoTag().delete(memoId)
    }
}
