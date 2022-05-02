package com.github.clientapplication.feature_github.data.rest

import com.github.clientapplication.feature_github.data.model.AccessToken
import com.github.clientapplication.utils.Constants.DOMAIN_URL
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface GithubApi {
    @Headers("Accept: application/json")
    @POST(DOMAIN_URL + "login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): AccessToken
}