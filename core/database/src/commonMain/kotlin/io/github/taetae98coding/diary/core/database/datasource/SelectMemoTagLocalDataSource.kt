package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class SelectMemoTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public fun page(accountId: Uuid, memoId: Uuid): PagingSource<Int, SelectMemoTagLocalEntity> {
        return database.selectMemoTag().page(accountId, memoId)
    }
}
