package io.github.taetae98coding.diary.core.work.worker.fetch

import io.github.taetae98coding.diary.core.database.datasource.AccountMemoLocalDataSource
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.AccountMemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class MemoFetchWorker(
    private val accountMemoLocalDataSource: AccountMemoLocalDataSource,
    private val accountMemoRemoteDataSource: AccountMemoRemoteDataSource,
) : EntityFetchWorker<MemoRemoteEntity>() {
    override suspend fun getLastUpdatedAt(accountId: Uuid): Long? {
        return accountMemoLocalDataSource.getLastUpdatedAt(accountId).first()
    }

    override suspend fun getFetchList(token: String, lastUpdatedAt: Long): List<MemoRemoteEntity> {
        return accountMemoRemoteDataSource.fetch(token, lastUpdatedAt)
            .requireSuccess()
            .requireBody()
    }

    override suspend fun fetch(accountId: Uuid, list: List<MemoRemoteEntity>) {
        accountMemoLocalDataSource.upsert(accountId, list.map(MemoRemoteEntity::toLocal))
    }
}
