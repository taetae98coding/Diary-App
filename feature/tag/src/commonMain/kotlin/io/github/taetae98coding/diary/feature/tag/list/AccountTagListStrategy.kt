package io.github.taetae98coding.diary.feature.tag.list

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.domain.tag.usecase.DeleteTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.FinishTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.PageTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.RestartTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.RestoreTagUseCase
import io.github.taetae98coding.diary.presenter.tag.list.TagListStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountTagListStrategy(
    private val pageTagUseCase: PageTagUseCase,
    private val finishTagUseCase: FinishTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
    private val restartTagUseCase: RestartTagUseCase,
    private val restoreTagUseCase: RestoreTagUseCase,
) : TagListStrategy {
    override fun pageTag(): Flow<Result<PagingData<Tag>>> {
        return pageTagUseCase()
    }
    override suspend fun fetch(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun finishTag(tagId: Uuid): Result<Unit> {
        return finishTagUseCase(tagId)
    }

    override suspend fun deleteTag(tagId: Uuid): Result<Unit> {
        return deleteTagUseCase(tagId)
    }

    override suspend fun restartTag(tagId: Uuid): Result<Unit> {
        return restartTagUseCase(tagId)
    }

    override suspend fun restoreTag(tagId: Uuid): Result<Unit> {
        return restoreTagUseCase(tagId)
    }
}
