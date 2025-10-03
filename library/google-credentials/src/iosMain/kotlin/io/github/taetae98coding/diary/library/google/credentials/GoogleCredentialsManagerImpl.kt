package io.github.taetae98coding.diary.library.google.credentials

import cocoapods.GoogleSignIn.GIDSignIn
import io.github.taetae98coding.diary.library.google.credentials.exception.GoogleCredentialCancellationException
import io.github.taetae98coding.diary.library.google.credentials.exception.GoogleCredentialException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
public class GoogleCredentialsManagerImpl(
    private val uiViewController: UIViewController,
) : GoogleCredentialsManager {
    override suspend fun getIdToken(): String {
        return suspendCancellableCoroutine { continuation ->
            GIDSignIn.sharedInstance.signInWithPresentingViewController(
                presentingViewController = uiViewController,
                completion = { result, error ->
                    if (!continuation.isActive) return@signInWithPresentingViewController

                    if (result != null) {
                        val idToken = result.user.idToken?.tokenString
                        if (idToken == null) {
                            continuation.resumeWithException(GoogleCredentialException())
                        } else {
                            continuation.resume(idToken)
                        }
                    } else if (error != null) {
                        when (error.code()) {
                            -5L -> continuation.resumeWithException(GoogleCredentialCancellationException())
                            else -> continuation.resumeWithException(GoogleCredentialException())
                        }
                    }
                },
            )
        }
    }
}
