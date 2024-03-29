package com.example.gamegenreshub.base

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.gamegenreshub.R

abstract class BaseFragment : Fragment() {

    private lateinit var viewModel: BaseViewModel

    fun setViewModel(viewModel: BaseViewModel) {
        this.viewModel = viewModel

        viewModel.errorObservable.observe(this) {
            showErrorMessage(it)
        }
    }

    fun showErrorMessage(message: String?) {
        AlertDialog.Builder(requireContext())
            .setMessage(message ?: getString(R.string.defautl_error_message))
            .setTitle(getString(R.string.error_title))
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog?.dismiss() }
            .show()
    }
}