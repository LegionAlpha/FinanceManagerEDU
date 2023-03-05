package com.legion.financemanager.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import com.legion.financemanager.data.data_source.local.FinanceManagerDatabase
import com.legion.financemanager.domain.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ExpenseFragmentViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val db: FinanceManagerDatabase
) : ViewModel() {

    val transactions = db.transactionDao.getTransactions(TransactionType.Expense)

    data class UiState(
        val isLoading: Boolean = false,
        val error: Error? = null,
    ) {
        sealed class Error {
            object AuthenticationError : Error()
            object NetworkError : Error()
            object UnknownError : Error()
        }
    }
}