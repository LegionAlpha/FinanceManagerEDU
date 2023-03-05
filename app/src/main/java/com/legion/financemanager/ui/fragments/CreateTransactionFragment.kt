package com.legion.financemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.legion.financemanager.R
import com.legion.financemanager.databinding.FragmentCreateTransactionBinding
import com.legion.financemanager.domain.enums.TransactionType
import com.legion.financemanager.ui.extensions.getEnum
import com.legion.financemanager.ui.util.launchWhenStarted
import com.legion.financemanager.ui.view_models.TransactionFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CreateTransactionFragment : Fragment() {

    companion object {
        const val TAG = "CreateTransactionFragment"
        const val ARG_TYPE = "arg_type"
    }

    private var _binding: FragmentCreateTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionFragmentViewModel by viewModels()

    private lateinit var type: TransactionType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = requireArguments().getEnum(ARG_TYPE)

        binding.tvTransactionType.text = getString(R.string.transaction_type, type.name)

        binding.btnCreateTransaction.setOnClickListener {
            viewModel.createTransaction(
                name = binding.etTitle.text.toString(),
                amount = binding.etAmount.text.toString().toDouble(),
                type = type,
            )
        }

        viewModel.eventsFlow.onEach { event ->
            when (event) {
                is TransactionFragmentViewModel.Event.ShowSnackBar -> {
                    Snackbar.make(
                        view,
                        event.text,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is TransactionFragmentViewModel.Event.ShowToast -> {
                    Toast.makeText(
                        requireContext(),
                        event.text,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}