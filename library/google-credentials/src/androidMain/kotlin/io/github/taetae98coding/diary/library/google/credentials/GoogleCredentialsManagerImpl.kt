package io.github.taetae98coding.diary.library.google.credentials

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.taetae98coding.diary.library.google.credentials.exception.GoogleCredentialCancellationException
import io.github.taetae98coding.diary.library.google.credentials.exception.GoogleCredentialException
import io.github.taetae98coding.diary.library.google.credentials.exception.GoogleCredentialNotExistException

public class GoogleCredentialsManagerImpl(
    private val context: Context,
) : GoogleCredentialsManager {
    override suspend fun getIdToken(): String {
        return try {
            val credentialsManager = CredentialManager.create(context)
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(GoogleCredentialsSdk.getServerClientId())
                .setAutoSelectEnabled(false)
                .setNonce(null)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialsManager.getCredential(context, request)
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)

            googleIdTokenCredential.idToken
        } catch (exception: GetCredentialCancellationException) {
            throw GoogleCredentialCancellationException(cause = exception)
        } catch (exception: NoCredentialException) {
            throw GoogleCredentialNotExistException(cause = exception)
        } catch (throwable: Throwable) {
            throw GoogleCredentialException(cause = throwable)
        }
    }
}
