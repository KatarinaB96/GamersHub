package com.example.gamegenreshub.presentation.info

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class InfoAdapter(
    fragmentManager : FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FirstInfoFragment()
        1 -> SecondInfoFragment()
        else -> throw IllegalArgumentException("Position must be between 0(inclusive) and $itemCount(exclusive)")
    }
}

