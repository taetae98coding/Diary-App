package io.github.taetae98coding.diary.data.tag.repository

import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.service.datasource.TagRemoteDataSource
import io.github.taetae98coding.diary.domain.tag.repository.TagRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
internal class TagRepositoryImpl(
    private val tagLocalDataSource: TagLocalDataSource,
) : TagRepository {

    override fun get(tagId: Uuid): Flow<Tag?> {
        return tagLocalDataSource.get(tagId)
            .mapLatest { it?.toEntity() }
    }
}
