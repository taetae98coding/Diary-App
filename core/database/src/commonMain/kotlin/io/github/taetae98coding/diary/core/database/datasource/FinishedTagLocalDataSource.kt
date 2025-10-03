package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class FinishedTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public fun page(accountId: Uuid): PagingSource<Int, TagLocalEntity> {
        return database.finishedTag().page(accountId)
    }
}
