package io.github.taetae98coding.diary.core.work.worker.fetch

import io.github.taetae98coding.diary.core.database.datasource.AccountMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.AccountMemoTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class MemoTagFetchWorker(
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val accountMemoTagLocalDataSource: AccountMemoTagLocalDataSource,
    private val accountMemoTagRemoteDataSource: AccountMemoTagRemoteDataSource,
) : EntityFetchWorker<MemoTagRemoteEntity>() {
    override suspend fun getLastUpdatedAt(accountId: Uuid): Long? {
        return accountMemoTagLocalDataSource.getLastUpdatedAt(accountId).first()
    }

    override suspend fun getFetchList(token: String, lastUpdatedAt: Long): List<MemoTagRemoteEntity> {
        return accountMemoTagRemoteDataSource.fetch(token, lastUpdatedAt)
            .requireSuccess()
            .requireBody()
    }

    override suspend fun fetch(accountId: Uuid, list: List<MemoTagRemoteEntity>) {
        memoTagLocalDataSource.upsert(list.map(MemoTagRemoteEntity::toLocal))
    }
}
