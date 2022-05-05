package com.github.clientapplication.feature_github.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.github.clientapplication.di.score.PreferenceInfo
import com.github.clientapplication.githubrepos.utils.Constants


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(
    context: Context,
    @PreferenceInfo preInfo: String
) {

    private val mPreference: SharedPreferences = context.getSharedPreferences(preInfo, Context.MODE_PRIVATE)

    var token: String?
        get() = mPreference.getString(Constants.PREF_KEY_TOKEN, null)
        set(token) = mPreference.edit().putString(Constants.PREF_KEY_TOKEN, token).apply()

}