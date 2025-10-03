package io.github.taetae98coding.diary.core.work.worker.backup

import io.github.taetae98coding.diary.core.database.datasource.AccountTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BackupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BackupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.TagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class TagBackupWorker(
    private val accountTagLocalDataSource: AccountTagLocalDataSource,
    private val backupTagLocalDataSource: BackupTagLocalDataSource,
    private val tagRemoteDataSource: TagRemoteDataSource,
) : EntityBackupWorker<TagLocalEntity>() {
    override suspend fun getBackupList(accountId: Uuid): List<TagLocalEntity> {
        return backupTagLocalDataSource.get(accountId).first()
    }

    override suspend fun backup(accountId: Uuid, token: String, list: List<TagLocalEntity>) {
        val remote = tagRemoteDataSource.upsert(token, list.map(TagLocalEntity::toRemote))
            .requireSuccess()
            .requireBody()

        accountTagLocalDataSource.upsert(accountId, remote.map(TagRemoteEntity::toLocal))
    }

    override suspend fun removeBackupList(accountId: Uuid, list: List<TagLocalEntity>) {
        backupTagLocalDataSource.delete(list.map { BackupTagLocalEntity(accountId, it.id) })
    }
}
