package com.github.clientapplication.feature_github.data.pref

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefManager @Inject constructor(
    private val mPreference: AppPreference
) {
    var currentToken: String?
        get() = this.mPreference.token
        set(token) {
            this.mPreference.token = token
        }
}
