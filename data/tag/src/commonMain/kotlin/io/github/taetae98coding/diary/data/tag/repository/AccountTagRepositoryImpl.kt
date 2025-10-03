package io.github.taetae98coding.diary.data.tag.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.AccountTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BackupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BackupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.domain.tag.repository.AccountTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountTagRepositoryImpl(
    private val transactor: DatabaseTransactor,
    private val accountTagLocalDataSource: AccountTagLocalDataSource,
    private val tagLocalDataSource: TagLocalDataSource,
    private val backupTagLocalDataSource: BackupTagLocalDataSource,
) : AccountTagRepository {
    override suspend fun add(account: Account, tag: Tag) {
        transactor.immediate {
            accountTagLocalDataSource.upsert(account.id, listOf(tag.toLocal()))
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupTagLocalDataSource.upsert(listOf(BackupTagLocalEntity(account.id, tag.id)))
            }
        }
    }

    override suspend fun update(account: Account, tagId: Uuid, detail: TagDetail) {
        transactor.immediate {
            tagLocalDataSource.update(tagId, detail.toLocal())
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupTagLocalDataSource.upsert(listOf(BackupTagLocalEntity(account.id, tagId)))
            }
        }
    }

    override suspend fun updateFinished(account: Account, tagId: Uuid, isFinished: Boolean) {
        transactor.immediate {
            tagLocalDataSource.updateFinished(tagId, isFinished)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupTagLocalDataSource.upsert(listOf(BackupTagLocalEntity(account.id, tagId)))
            }
        }
    }

    override suspend fun updateDeleted(account: Account, tagId: Uuid, isDeleted: Boolean) {
        transactor.immediate {
            tagLocalDataSource.updateDeleted(tagId, isDeleted)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupTagLocalDataSource.upsert(listOf(BackupTagLocalEntity(account.id, tagId)))
            }
        }
    }

    override fun page(account: Account): Flow<PagingData<Tag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { accountTagLocalDataSource.page(account.id) },
        )

        return pager.flow.mapPagingLatest(TagLocalEntity::toEntity)
    }
}
