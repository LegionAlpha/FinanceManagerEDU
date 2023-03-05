package com.legion.financemanager.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.legion.financemanager.R
import com.legion.financemanager.data.data_source.local.entity.TransactionEntity
import com.legion.financemanager.databinding.RecyclerViewTransactionListItemBinding
import com.legion.financemanager.domain.enums.TransactionType

class TransactionsAdapter(
    private val transactions: List<TransactionEntity>
) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerViewTransactionListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewTransactionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val transaction = transactions[position]
            when (transaction.type) {
                TransactionType.Income -> {
                    tvAmount.setTextColor(
                        ContextCompat.getColor(holder.itemView.context, R.color.green)
                    )
                    tvAmount.text = "+${transaction.amount}"
                }
                TransactionType.Expense -> {
                    tvAmount.setTextColor(
                        ContextCompat.getColor(holder.itemView.context, R.color.red)
                    )
                    tvAmount.text = "-${transaction.amount}"
                }
            }

            tvName.text = transaction.name
        }
    }

    override fun getItemCount(): Int {
        return transactions.count()
    }
}