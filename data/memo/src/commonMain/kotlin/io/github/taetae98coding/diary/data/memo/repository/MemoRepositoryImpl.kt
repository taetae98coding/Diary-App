package io.github.taetae98coding.diary.data.memo.repository

import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.MemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.domain.memo.exception.MemoNotFoundException
import io.github.taetae98coding.diary.domain.memo.repository.MemoRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
internal class MemoRepositoryImpl(
    private val memoLocalDataSource: MemoLocalDataSource,
    private val memoRemoteDataSource: MemoRemoteDataSource,
) : MemoRepository {
    override suspend fun fetch(account: Account.User, memoId: Uuid) {
        val response = memoRemoteDataSource.get(account.token, memoId)

        when (response.code) {
            DiaryRemoteEntity.NOT_FOUND -> throw MemoNotFoundException()

            else -> {
                val remote = response.requireSuccess()
                    .requireBody()

                memoLocalDataSource.upsert(listOf(remote.toLocal()))
            }
        }
    }

    override fun get(memoId: Uuid): Flow<Memo?> {
        return memoLocalDataSource.get(memoId)
            .mapLatest { it?.toEntity() }
    }
}
