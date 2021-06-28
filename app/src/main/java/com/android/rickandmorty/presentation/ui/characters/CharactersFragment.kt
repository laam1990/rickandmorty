package com.android.rickandmorty.presentation.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.android.rickandmorty.commons.fragment.BaseFragment
import com.android.rickandmorty.commons.view.ViewState
import com.android.rickandmorty.commons.view.gone
import com.android.rickandmorty.commons.view.visible
import com.android.rickandmorty.databinding.FragmentCharactersBinding
import com.android.rickandmorty.presentation.ui.viewModel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    private val characterAdapter by lazy { CharacterAdapter() }
    private val viewModel by viewModels<CharacterViewModel>()

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(inflater, container, false)

    override fun initView() {
        statusBar()
        binding.charactersRv.adapter = characterAdapter
        viewModel.queryCharactersList()

        characterAdapter.onItemClicked = { character ->
            character.let {
                if (!character.id.isNullOrBlank()) {
                    findNavController().navigate(
                        CharactersFragmentDirections.navigateToCharacterDetailsFragment(
                            id = character.id
                        )
                    )
                }
            }
        }
    }

    override fun initViewModel() {
        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.charactersList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    binding.charactersRv.gone()
                    binding.charactersFetchProgress.visible()
                }
                is ViewState.Success -> {
                    if (response.value?.data?.characters?.results?.size == 0) {
                        characterAdapter.submitList(emptyList())
                        binding.charactersFetchProgress.gone()
                        binding.charactersRv.gone()
                        binding.charactersEmptyText.visible()
                    } else {
                        binding.charactersRv.visible()
                        binding.charactersEmptyText.gone()
                    }
                    val results = response.value?.data?.characters?.results
                    characterAdapter.submitList(results)
                    binding.charactersFetchProgress.gone()
                }
                is ViewState.Error -> {
                    characterAdapter.submitList(emptyList())
                    binding.charactersFetchProgress.gone()
                    binding.charactersRv.gone()
                    binding.charactersEmptyText.visible()
                }
            }
        }
    }

}