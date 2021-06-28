package com.android.rickandmorty.commons.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.android.rickandmorty.presentation.ui.viewModel.CharacterViewModel

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): T

    protected abstract fun initView()

    protected abstract fun initViewModel()

    protected lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = setBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initView()
    }

    fun statusBar() {
        val w: Window = requireActivity().window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

}