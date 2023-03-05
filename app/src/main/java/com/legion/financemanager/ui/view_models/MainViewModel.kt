package com.legion.financemanager.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import com.legion.financemanager.data.data_source.local.FinanceManagerDatabase
import com.legion.financemanager.data.data_source.local.entity.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val db: FinanceManagerDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val transactions = db.transactionDao.getAllTransactions()

    data class UiState(
        val isLoading: Boolean = false,
        val error: Error? = null,
        val transactions: Flow<List<TransactionEntity>> = emptyFlow()
    ) {
        sealed class Error {
            object AuthenticationError : Error()
            object NetworkError : Error()
            object UnknownError : Error()
        }
    }
}