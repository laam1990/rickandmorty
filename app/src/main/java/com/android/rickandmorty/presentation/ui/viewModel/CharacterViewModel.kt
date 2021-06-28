package com.android.rickandmorty.presentation.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.rickandmorty.CharacterQuery
import com.android.rickandmorty.CharactersListQuery
import com.android.rickandmorty.commons.view.ViewState
import com.android.rickandmorty.data.repository.Repository
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val mCharactersList by lazy { MutableLiveData<ViewState<Response<CharactersListQuery.Data>>>() }
    val charactersList: LiveData<ViewState<Response<CharactersListQuery.Data>>>
        get() = mCharactersList

    private val mCharacter by lazy { MutableLiveData<ViewState<Response<CharacterQuery.Data>>>() }
    val character: LiveData<ViewState<Response<CharacterQuery.Data>>>
        get() = mCharacter

    fun queryCharactersList() = viewModelScope.launch {
        mCharactersList.postValue(ViewState.Loading())
        try {
            val response = repository.getCharacters()
            mCharactersList.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            mCharactersList.postValue(ViewState.Error("Error"))
        }
    }

    fun queryCharacter(id: String) = viewModelScope.launch {
        mCharacter.postValue(ViewState.Loading())
        try {
            val response = repository.getCharacterDetail(id)
            mCharacter.postValue(ViewState.Success(response))
        } catch (ae: ApolloException) {
            mCharacter.postValue(ViewState.Error("Error fetching characters"))
        }
    }

}