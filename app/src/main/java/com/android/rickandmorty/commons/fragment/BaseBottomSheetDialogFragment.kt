package com.android.rickandmorty.commons.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import com.android.rickandmorty.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment() {

    protected lateinit var binding: T

    abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): T

    protected abstract fun initView()

    protected abstract fun initViewModel()

    protected abstract fun layoutId() : Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = setBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initView()
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, layoutId(), null)
        dialog.setContentView(contentView)
        backgroundTransparent(contentView.parent as View)
    }

    private fun backgroundTransparent(view: View) {
        val params = view.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        view.setBackgroundColor(Color.TRANSPARENT)
    }
}