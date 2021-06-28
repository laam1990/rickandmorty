package com.android.rickandmorty.data.network

import android.os.Looper
import com.android.rickandmorty.data.util.Constant.BASE_URL
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class ApolloClient {

    fun getApolloClient(): ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }

        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}