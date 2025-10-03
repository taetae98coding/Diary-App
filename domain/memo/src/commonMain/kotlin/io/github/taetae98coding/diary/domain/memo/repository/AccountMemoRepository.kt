package io.github.taetae98coding.diary.domain.memo.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import kotlin.uuid.Uuid

public interface AccountMemoRepository {
    public suspend fun add(account: Account, memo: Memo, memoTagIds: Set<Uuid>)
    public suspend fun update(account: Account, memoId: Uuid, detail: MemoDetail)
    public suspend fun updateFinished(account: Account, memoId: Uuid, isFinished: Boolean)
    public suspend fun updateDeleted(account: Account, memoId: Uuid, isDeleted: Boolean)
    public suspend fun updatePrimaryTag(account: Account, memoId: Uuid, tagId: Uuid?)
}
