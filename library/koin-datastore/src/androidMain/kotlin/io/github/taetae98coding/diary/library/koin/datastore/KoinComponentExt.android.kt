package io.github.taetae98coding.diary.library.koin.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public actual fun KoinComponent.deviceProtectedDataStore(fileName: String): DataStore<Preferences> {
    val context by inject<Context>()

    return DataStoreFactory.createInDeviceProtectedStorage(
        context = context,
        fileName = fileName,
        serializer = PreferencesFileSerializer,
    )
}
