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
        //        2 -> ThirdOnboardingFragment()
        else -> throw IllegalArgumentException("Position must be between 0(inclusive) and $itemCount(exclusive)")
    }
}

//class InfoAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    override fun getItem(position: Int): Fragment {
//        // Return your fragment instance based on the position
//        // Example:
//        return when (position) {
//            0 -> FirstInfoFragment()
//            1 -> SecondInfoFragment()
//            else -> throw IllegalArgumentException("Invalid position $position")
//        }
//    }
//
//    override fun getCount(): Int {
//        return 2
//    }
//}