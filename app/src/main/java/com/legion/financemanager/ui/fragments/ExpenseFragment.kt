package com.legion.financemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.legion.financemanager.R
import com.legion.financemanager.databinding.FragmentExpenseBinding
import com.legion.financemanager.domain.enums.TransactionType
import com.legion.financemanager.ui.adapters.TransactionsAdapter
import com.legion.financemanager.ui.extensions.putEnum
import com.legion.financemanager.ui.util.launchWhenStarted
import com.legion.financemanager.ui.view_models.ExpenseFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExpenseFragment : Fragment() {

    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddExpense.setOnClickListener {
            // open create transaction screen with type: Income
            val bundle = Bundle()
            bundle.putEnum(CreateTransactionFragment.ARG_TYPE, TransactionType.Expense)

            val createTransactionFragment = CreateTransactionFragment()
            createTransactionFragment.arguments = bundle

            val transaction = parentFragmentManager.beginTransaction()
            transaction.addToBackStack(CreateTransactionFragment.TAG)
            transaction.replace(R.id.rootLayout, createTransactionFragment).commit()
        }

        viewModel.transactions.onEach {
            binding.rvTransactions.adapter = TransactionsAdapter(
                transactions = it
            )
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}