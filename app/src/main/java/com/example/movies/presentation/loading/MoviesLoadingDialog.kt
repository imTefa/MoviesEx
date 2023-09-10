package com.example.movies.presentation.loading

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.movies.databinding.DialogLoadingBinding

class MoviesLoadingDialog : DialogFragment() {
    private lateinit var binding: DialogLoadingBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun show(manager: FragmentManager) {
        if (isAdded.not())
            try {
                show(manager, TAG)
            }catch (e: Exception){
                //Ignroe
            }

    }

    override fun dismiss() {
        if (isAdded)
            super.dismiss()
    }

    companion object {

        private const val TAG = "DowidarLoadingDialog"

        fun newInstance(): MoviesLoadingDialog {
            return MoviesLoadingDialog()
        }
    }

}