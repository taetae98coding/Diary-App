package io.github.taetae98coding.diary.core.work.worker.fetch

import io.github.taetae98coding.diary.core.database.datasource.AccountTagLocalDataSource
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.preferences.datasource.TagFetchPreferencesDataSource
import io.github.taetae98coding.diary.core.service.datasource.AccountTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class TagFetchWorker(
    private val accountTagLocalDataSource: AccountTagLocalDataSource,
    private val tagFetchPreferencesDataSource: TagFetchPreferencesDataSource,
    private val accountTagRemoteDataSource: AccountTagRemoteDataSource,
) : EntityFetchWorker<TagRemoteEntity>() {
    override suspend fun getLastUpdatedAt(accountId: Uuid): Long? {
        return tagFetchPreferencesDataSource.getLastUpdatedAt(accountId).first()
    }

    override suspend fun getFetchList(token: String, lastUpdatedAt: Long): List<TagRemoteEntity> {
        return accountTagRemoteDataSource.fetch(token, lastUpdatedAt)
            .requireSuccess()
            .requireBody()
    }

    override suspend fun fetch(accountId: Uuid, list: List<TagRemoteEntity>) {
        accountTagLocalDataSource.upsert(accountId, list.map(TagRemoteEntity::toLocal))
    }

    override suspend fun updateLastUpdatedAt(accountId: Uuid, list: List<TagRemoteEntity>) {
        tagFetchPreferencesDataSource.updateLastUpdatedAt(accountId, list.maxOf { it.updatedAt })
    }
}
