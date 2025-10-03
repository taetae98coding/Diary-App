package io.github.taetae98coding.diary.feature.tag.detail

import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.navigation.parameter.TagId
import io.github.taetae98coding.diary.domain.tag.usecase.DeleteTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.FinishTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.GetTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.RestartTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.UpdateTagUseCase
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailStrategy
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountTagDetailStrategy(
    private val tagId: TagId,
    private val getTagUseCase: GetTagUseCase,
    private val updateTagUseCase: UpdateTagUseCase,
    private val finishTagUseCase: FinishTagUseCase,
    private val restartTagUseCase: RestartTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
) : TagDetailStrategy {
    override fun getTag(): Flow<Result<Tag?>> {
        return getTagUseCase(tagId.value)
    }

    override suspend fun fetch(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun finishTag(): Result<Unit> {
        return finishTagUseCase(tagId.value)
    }

    override suspend fun restartTag(): Result<Unit> {
        return restartTagUseCase(tagId.value)
    }

    override suspend fun deleteTag(): Result<Unit> {
        return deleteTagUseCase(tagId.value)
    }

    override suspend fun updateTag(detail: TagDetail): Result<Unit> {
        return updateTagUseCase(tagId.value, detail)
    }
}
