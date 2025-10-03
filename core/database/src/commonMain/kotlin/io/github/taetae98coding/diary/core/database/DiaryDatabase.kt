package io.github.taetae98coding.diary.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.taetae98coding.diary.core.database.dao.AccountBuddyGroupDao
import io.github.taetae98coding.diary.core.database.dao.AccountMemoDao
import io.github.taetae98coding.diary.core.database.dao.AccountTagDao
import io.github.taetae98coding.diary.core.database.dao.BackupMemoDao
import io.github.taetae98coding.diary.core.database.dao.BackupMemoTagDao
import io.github.taetae98coding.diary.core.database.dao.BackupTagDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupCalendarMemoDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupMemoDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupSelectMemoTagDao
import io.github.taetae98coding.diary.core.database.dao.BuddyGroupTagDao
import io.github.taetae98coding.diary.core.database.dao.CalendarMemoDao
import io.github.taetae98coding.diary.core.database.dao.CalendarMemoTagFilterDao
import io.github.taetae98coding.diary.core.database.dao.ListMemoDao
import io.github.taetae98coding.diary.core.database.dao.ListMemoTagFilterDao
import io.github.taetae98coding.diary.core.database.dao.MemoDao
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
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.ListMemoTagFilterLocalEntity
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
        ListMemoTagFilterLocalEntity::class,
        CalendarMemoTagFilterLocalEntity::class,

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
    abstract fun listMemo(): ListMemoDao
    abstract fun accountTag(): AccountTagDao
    abstract fun backupMemo(): BackupMemoDao
    abstract fun backupTag(): BackupTagDao
    abstract fun backupMemoTag(): BackupMemoTagDao
    abstract fun buddyGroupCalendarMemo(): BuddyGroupCalendarMemoDao
    abstract fun buddyGroup(): BuddyGroupDao
    abstract fun buddyGroupMemo(): BuddyGroupMemoDao
    abstract fun buddyGroupTag(): BuddyGroupTagDao
    abstract fun buddyGroupSelectMemoTag(): BuddyGroupSelectMemoTagDao
    abstract fun calendarMemo(): CalendarMemoDao
    abstract fun memo(): MemoDao
    abstract fun memoTag(): MemoTagDao
    abstract fun listMemoTagFilter(): ListMemoTagFilterDao
    abstract fun calendarMemoTagFilter(): CalendarMemoTagFilterDao
    abstract fun tag(): TagDao
    abstract fun tagMemo(): TagMemoDao
    abstract fun selectMemoTag(): SelectMemoTagDao
}
