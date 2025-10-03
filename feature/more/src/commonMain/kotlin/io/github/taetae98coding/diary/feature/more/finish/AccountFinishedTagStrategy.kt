package io.github.taetae98coding.diary.feature.more.finish

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.domain.tag.usecase.DeleteTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.PageFinishedTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.RestoreTagUseCase
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountFinishedTagStrategy(
    private val pageFinishedTagUseCase: PageFinishedTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
    private val restoreTagUseCase: RestoreTagUseCase,
) : FinishedTagStrategy {
    override fun page(): Flow<Result<PagingData<Tag>>> {
        return pageFinishedTagUseCase()
    }

    override suspend fun delete(tagId: Uuid): Result<Unit> {
        return deleteTagUseCase(tagId)
    }

    override suspend fun restore(tagId: Uuid): Result<Unit> {
        return restoreTagUseCase(tagId)
    }
}
