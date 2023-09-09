package com.example.movies.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.movies.presentation.loading.MoviesLoadingDialog

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected lateinit var binding: V
    abstract val layout: Int

    private var loadingDialog: MoviesLoadingDialog? = null
        get() {
            if (field == null)
                field = MoviesLoadingDialog.newInstance()
            return field
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
    }

    abstract fun observeUiState()

    fun showErrorIndicator(errorUiState: ErrorUiState) {
        //implement whatever error indicator we need, I chose toasts for this small task
        toast(errorUiState.getMessage(requireContext()))
    }

    fun showLoadingIndicator(isLoading: Boolean) {
        ///implement whatever loading indicator we need, I chose common loading dialog for this small task
        if (isLoading)
            loadingDialog!!.show(childFragmentManager)
        else loadingDialog!!.dismiss()
    }

    fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}