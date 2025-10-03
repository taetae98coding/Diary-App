package io.github.taetae98coding.diary.library.koin.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import java.io.File
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent

public actual fun KoinComponent.deviceProtectedDataStore(
    fileName: String,
): DataStore<Preferences> {
    val file = File(KoinComponentDataStore.storePath(), fileName)

    runCatching { file.parentFile?.mkdirs() }
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { file.absolutePath.toPath() },
    )
}
