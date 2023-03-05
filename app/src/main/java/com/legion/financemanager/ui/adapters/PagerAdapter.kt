package com.legion.financemanager.ui.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.legion.financemanager.ui.fragments.ExpenseFragment
import com.legion.financemanager.ui.fragments.IncomeFragment

class PagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ExpenseFragment()
            1 -> IncomeFragment()
            else -> throw Resources.NotFoundException("Position not found!")
        }
    }

}