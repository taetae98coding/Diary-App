package io.github.taetae98coding.diary.core.work.worker.fetch

import io.github.taetae98coding.diary.core.database.datasource.AccountTagLocalDataSource
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.AccountTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class TagFetchWorker(
    private val accountTagLocalDataSource: AccountTagLocalDataSource,
    private val accountTagRemoteDataSource: AccountTagRemoteDataSource,
) : EntityFetchWorker<TagRemoteEntity>() {
    override suspend fun getLastUpdatedAt(accountId: Uuid): Long? {
        return accountTagLocalDataSource.getLastUpdatedAt(accountId).first()
    }

    override suspend fun getFetchList(token: String, lastUpdatedAt: Long): List<TagRemoteEntity> {
        return accountTagRemoteDataSource.fetch(token, lastUpdatedAt)
            .requireSuccess()
            .requireBody()
    }

    override suspend fun fetch(accountId: Uuid, list: List<TagRemoteEntity>) {
        accountTagLocalDataSource.upsert(accountId, list.map(TagRemoteEntity::toLocal))
    }
}
