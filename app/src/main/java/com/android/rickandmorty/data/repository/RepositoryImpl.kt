package com.android.rickandmorty.data.repository

import com.android.rickandmorty.CharacterQuery
import com.android.rickandmorty.CharactersListQuery
import com.android.rickandmorty.data.network.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) : Repository {

    override suspend fun getCharacters(): Response<CharactersListQuery.Data> =
        apolloClient.getApolloClient().query(CharactersListQuery()).await()

    override suspend fun getCharacterDetail(id: String): Response<CharacterQuery.Data> =
        apolloClient.getApolloClient().query(CharacterQuery(id)).await()


}