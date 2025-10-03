package io.github.taetae98coding.diary.data.tag.repository

import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.TagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.domain.tag.exception.TagNotFoundException
import io.github.taetae98coding.diary.domain.tag.repository.TagRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
internal class TagRepositoryImpl(
    private val tagLocalDataSource: TagLocalDataSource,
    private val tagRemoteDataSource: TagRemoteDataSource,
) : TagRepository {
    override suspend fun fetch(account: Account.User, tagId: Uuid) {
        val response = tagRemoteDataSource.get(account.token, tagId)

        when (response.code) {
            DiaryRemoteEntity.NOT_FOUND -> throw TagNotFoundException()

            else -> {
                val remote = response.requireSuccess()
                    .requireBody()

                tagLocalDataSource.upsert(listOf(remote.toLocal()))
            }
        }
    }

    override fun get(tagId: Uuid): Flow<Tag?> {
        return tagLocalDataSource.get(tagId)
            .mapLatest { it?.toEntity() }
    }
}
