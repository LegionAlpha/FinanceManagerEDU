package com.legion.financemanager.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legion.financemanager.data.data_source.local.FinanceManagerDatabase
import com.legion.financemanager.data.data_source.local.entity.TransactionEntity
import com.legion.financemanager.domain.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionFragmentViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val db: FinanceManagerDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(isLoading = false))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun createTransaction(
        name: String,
        amount: Double,
        type: TransactionType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val transactionEntity = TransactionEntity(
                id = 0,
                categoryId = 0,
                name = name,
                amount = amount,
                comment = null,
                date = Date(),
                type = type,
            )
            val id = db.transactionDao.insertTransaction(transactionEntity)
            eventChannel.send(Event.ShowSnackBar("Successfully created!"))
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val isSuccessfullyCreated: Boolean = false,
    )

    sealed class Event {
        data class ShowSnackBar(val text: String): Event()
        data class ShowToast(val text: String): Event()
    }
}