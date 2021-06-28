package com.android.rickandmorty.data.repository

import com.android.rickandmorty.CharacterQuery
import com.android.rickandmorty.CharactersListQuery
import com.apollographql.apollo.api.Response

interface Repository {

    suspend fun getCharacters() : Response<CharactersListQuery.Data>

    suspend fun getCharacterDetail(id: String) : Response<CharacterQuery.Data>

}