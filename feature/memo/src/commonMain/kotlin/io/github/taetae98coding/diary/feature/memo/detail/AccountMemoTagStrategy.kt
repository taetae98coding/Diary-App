package io.github.taetae98coding.diary.feature.memo.detail

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.navigation.parameter.MemoId
import io.github.taetae98coding.diary.domain.memo.usecase.GetMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.RemoveMemoPrimaryTagUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.SelectMemoPrimaryTagUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.SelectMemoTagUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.UnselectMemoTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.PageSelectMemoTagUseCase
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountMemoTagStrategy(
    private val memoId: MemoId,
    private val getMemoUseCase: GetMemoUseCase,
    private val pageSelectMemoTagUseCase: PageSelectMemoTagUseCase,
    private val selectMemoPrimaryTagUseCase: SelectMemoPrimaryTagUseCase,
    private val removeMemoPrimaryTagUseCase: RemoveMemoPrimaryTagUseCase,
    private val selectMemoTagUseCase: SelectMemoTagUseCase,
    private val unselectMemoTagUseCase: UnselectMemoTagUseCase,
) : MemoTagStrategy {
    override fun getMemo(): Flow<Result<Memo?>> {
        return getMemoUseCase(memoId.value)
    }

    override suspend fun fetch(): Result<Unit> {
        return Result.success(Unit)
    }

    override fun pageSelectMemoTag(): Flow<Result<PagingData<SelectMemoTag>>> {
        return pageSelectMemoTagUseCase(memoId.value)
    }

    override suspend fun selectPrimaryTag(tagId: Uuid): Result<Unit> {
        return selectMemoPrimaryTagUseCase(memoId.value, tagId)
    }

    override suspend fun removePrimaryTag(): Result<Unit> {
        return removeMemoPrimaryTagUseCase(memoId.value)
    }

    override suspend fun selectMemoTag(tagId: Uuid): Result<Unit> {
        return selectMemoTagUseCase(memoId.value, tagId)
    }

    override suspend fun unselectMemoTag(tagId: Uuid): Result<Unit> {
        return unselectMemoTagUseCase(memoId.value, tagId)
    }
}
