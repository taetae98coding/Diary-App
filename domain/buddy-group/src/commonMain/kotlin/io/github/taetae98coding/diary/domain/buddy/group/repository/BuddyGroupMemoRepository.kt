package io.github.taetae98coding.diary.domain.buddy.group.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import kotlin.uuid.Uuid

public interface BuddyGroupMemoRepository {
    public suspend fun add(account: Account.User, buddyGroupId: Uuid, detail: MemoDetail, primaryTag: Uuid?, memoTagIds: Set<Uuid>)
    public suspend fun update(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, detail: MemoDetail)
    public suspend fun updateFinished(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, isFinished: Boolean)
    public suspend fun updateDeleted(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, isDeleted: Boolean)
    public suspend fun updatePrimaryTag(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, tagId: Uuid?)
}
