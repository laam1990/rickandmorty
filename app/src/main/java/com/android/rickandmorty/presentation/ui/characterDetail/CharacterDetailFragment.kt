package com.android.rickandmorty.presentation.ui.characterDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import coil.load
import com.android.rickandmorty.R
import com.android.rickandmorty.commons.fragment.BaseBottomSheetDialogFragment
import com.android.rickandmorty.commons.view.ViewState
import com.android.rickandmorty.commons.view.gone
import com.android.rickandmorty.commons.view.visible
import com.android.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.android.rickandmorty.presentation.ui.viewModel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetailFragment : BaseBottomSheetDialogFragment<FragmentCharacterDetailBinding>() {

    private val args: CharacterDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<CharacterViewModel>()

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCharacterDetailBinding =
        FragmentCharacterDetailBinding.inflate(inflater, container, false)

    override fun layoutId(): Int = R.layout.fragment_character_detail

    override fun initView() {
        viewModel.queryCharacter(args.id)
    }

    override fun initViewModel() {
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.character.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    binding.characterDetailsFetchProgress.visible()
                    binding.characterDetailsNotFound.gone()
                }
                is ViewState.Success -> {
                    if (response.value?.data?.character == null) {
                        binding.characterDetailsFetchProgress.gone()
                        binding.characterDetailsNotFound.visible()
                    } else {
                        binding.query = response.value.data
                        binding.characterDetailsFetchProgress.gone()
                        binding.characterDetailsNotFound.gone()
                    }
                }
                is ViewState.Error -> {
                    binding.characterDetailsFetchProgress.gone()
                    binding.characterDetailsNotFound.visible()
                }
            }
        }
    }


}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
    }
}