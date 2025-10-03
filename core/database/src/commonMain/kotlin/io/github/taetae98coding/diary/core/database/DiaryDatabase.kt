package io.github.taetae98coding.diary.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.taetae98coding.diary.core.database.dao.AccountBuddyGroupDao
import io.github.taetae98coding.diary.core.database.dao.AccountMemoDao
import io.github.taetae98coding.diary.core.database.dao.AccountMemoTagDao
import io.github.taetae98coding.diary.core.database.dao.AccountTagDao
import io.github.taetae98coding.diary.core.database.dao.BackupMemoDao
import io.github.taetae98coding.diary.core.database.dao.BackupMemoTagDao
import io.github.taetae98coding.diary.core.database.dao.BackupTagDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupFinishedMemoDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupFinishedTagDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupMemoCalendarDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupMemoCalendarTagFilterDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupMemoDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupMemoListDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupMemoListTagFilterDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupMemoTagDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupSelectMemoTagDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupTagDao
import io.github.taetae98coding.diary.core.database.dao.FinishedMemoListDao
import io.github.taetae98coding.diary.core.database.dao.FinishedTagDao
import io.github.taetae98coding.diary.core.database.dao.MemoCalendarDao
import io.github.taetae98coding.diary.core.database.dao.MemoCalendarTagFilterDao
import io.github.taetae98coding.diary.core.database.dao.MemoDao
import io.github.taetae98coding.diary.core.database.dao.MemoListDao
import io.github.taetae98coding.diary.core.database.dao.MemoListTagFilterDao
import io.github.taetae98coding.diary.core.database.dao.MemoTagDao
import io.github.taetae98coding.diary.core.database.dao.SelectMemoTagDao
import io.github.taetae98coding.diary.core.database.dao.TagDao
import io.github.taetae98coding.diary.core.database.dao.TagMemoDao
import io.github.taetae98coding.diary.core.database.entity.AccountBuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.entity.AccountMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.AccountTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BackupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BackupMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BackupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoCalendarTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoListTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.library.room.common.converter.LocalDateConverter
import io.github.taetae98coding.diary.library.room.common.converter.UuidConverter

@Database(
    entities = [
        TagLocalEntity::class,
        MemoLocalEntity::class,
        BuddyGroupLocalEntity::class,

        MemoTagLocalEntity::class,
        MemoListTagFilterLocalEntity::class,
        MemoCalendarTagFilterLocalEntity::class,

        BackupMemoLocalEntity::class,
        BackupTagLocalEntity::class,
        BackupMemoTagLocalEntity::class,

        AccountBuddyGroupLocalEntity::class,
        AccountMemoLocalEntity::class,
        AccountTagLocalEntity::class,

        BuddyGroupMemoLocalEntity::class,
        BuddyGroupTagLocalEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    value = [
        UuidConverter::class,
        LocalDateConverter::class,
    ],
)
@ConstructedBy(DiaryDatabaseConstructor::class)
internal abstract class DiaryDatabase : RoomDatabase() {
    abstract fun accountBuddyGroup(): AccountBuddyGroupDao
    abstract fun accountMemo(): AccountMemoDao
    abstract fun accountMemoTag(): AccountMemoTagDao
    abstract fun memoList(): MemoListDao
    abstract fun accountTag(): AccountTagDao
    abstract fun backupMemo(): BackupMemoDao
    abstract fun backupTag(): BackupTagDao
    abstract fun backupMemoTag(): BackupMemoTagDao
    abstract fun buddyGroupMemoCalendar(): BuddyGroupMemoCalendarDao
    abstract fun buddyGroup(): BuddyGroupDao
    abstract fun buddyGroupMemo(): BuddyGroupMemoDao
    abstract fun buddyGroupMemoList(): BuddyGroupMemoListDao
    abstract fun buddyGroupTag(): BuddyGroupTagDao
    abstract fun buddyGroupMemoTag(): BuddyGroupMemoTagDao
    abstract fun buddyGroupSelectMemoTag(): BuddyGroupSelectMemoTagDao
    abstract fun buddyGroupMemoListTagFilter(): BuddyGroupMemoListTagFilterDao
    abstract fun memoCalendar(): MemoCalendarDao
    abstract fun memo(): MemoDao
    abstract fun memoTag(): MemoTagDao
    abstract fun memoListTagFilter(): MemoListTagFilterDao
    abstract fun memoCalendarTagFilter(): MemoCalendarTagFilterDao
    abstract fun buddyGroupMemoCalendarTagFilter(): BuddyGroupMemoCalendarTagFilterDao
    abstract fun finishedMemoList(): FinishedMemoListDao
    abstract fun buddyGroupFinishedMemo(): BuddyGroupFinishedMemoDao
    abstract fun buddyGroupFinishedTag(): BuddyGroupFinishedTagDao
    abstract fun tag(): TagDao
    abstract fun tagMemo(): TagMemoDao
    abstract fun finishedTag(): FinishedTagDao
    abstract fun selectMemoTag(): SelectMemoTagDao
}
