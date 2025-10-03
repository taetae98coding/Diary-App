package io.github.taetae98coding.diary.library.google.credentials

public data object GoogleCredentialsSdk {
    private var redirectPort: Int? = null
    private var redirectUri: String? = null
    private var clientId: String? = null
    private var clientSecret: String? = null

    public fun redirectUri(redirectUri: String): GoogleCredentialsSdk {
        redirectPort = redirectUri.substringAfter("://")
            .substringAfter(":")
            .substringBefore("?")
            .substringBefore("/")
            .toIntOrNull()

        this.redirectUri = redirectUri
        return this
    }

    public fun clientId(clientId: String): GoogleCredentialsSdk {
        this.clientId = clientId
        return this
    }

    public fun clientSecret(clientSecret: String): GoogleCredentialsSdk {
        this.clientSecret = clientSecret
        return this
    }

    internal fun getRedirectPort(): Int {
        return requireNotNull(redirectPort)
    }

    internal fun getRedirectUri(): String {
        return requireNotNull(redirectUri)
    }

    internal fun getClientId(): String {
        return requireNotNull(clientId)
    }

    internal fun getClientSecret(): String {
        return requireNotNull(clientSecret)
    }
}
