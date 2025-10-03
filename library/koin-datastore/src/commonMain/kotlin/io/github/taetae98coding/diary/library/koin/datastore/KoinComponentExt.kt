package io.github.taetae98coding.diary.library.koin.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.component.KoinComponent

public expect fun KoinComponent.deviceProtectedDataStore(fileName: String): DataStore<Preferences>
