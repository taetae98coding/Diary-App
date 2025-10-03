package io.github.taetae98coding.diary.library.koin.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.github.taetae98coding.diary.library.kotlinx.file.documentDirectory
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import platform.Foundation.NSFileManager

@OptIn(ExperimentalForeignApi::class)
public actual fun KoinComponent.deviceProtectedDataStore(
    fileName: String,
): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { "${requireNotNull(NSFileManager.documentDirectory())}/$fileName".toPath() },
    )
}
