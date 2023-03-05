package com.legion.financemanager.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.legion.financemanager.R
import com.legion.financemanager.data.data_source.local.FinanceManagerDatabase
import com.legion.financemanager.databinding.ActivityMainBinding
import com.legion.financemanager.domain.enums.TransactionType
import com.legion.financemanager.ui.adapters.PagerAdapter
import com.legion.financemanager.ui.util.launchWhenStarted
import com.legion.financemanager.ui.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private val viewModel: MainViewModel by viewModels()

    @Inject lateinit var db: FinanceManagerDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.transactions.onEach { list ->
            val incomeTransactions = list.filter { it.type == TransactionType.Income }
            val expenseTransactions = list.filter { it.type == TransactionType.Expense }
            val totalBalance =
                incomeTransactions.sumOf { it.amount } - expenseTransactions.sumOf { it.amount }
            binding.tvTotalBalance.text = resources.getString(
                R.string.total_balance,
                totalBalance
            )
        }.launchWhenStarted(lifecycleScope)

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, index ->
            tab.text = when (index) {
                0 -> "Expense"
                1 -> "Income"
                else -> throw Resources.NotFoundException("Position not found!")
            }
        }.attach()

//        viewModel.transactions.onEach {
//            binding.rvTransactions.adapter = TransactionsAdapter(
//                transactions = it
//            )
//        }.launchWhenStarted(lifecycleScope)
//
//        binding.btnAddTransaction.setOnClickListener {
//            launch {
//                createTransactionEntity()
//            }
//        }
    }

    fun showProgressBar() {
        binding.progressBar.progressBarLayout.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.progressBar.progressBarLayout.visibility = View.GONE
    }
}