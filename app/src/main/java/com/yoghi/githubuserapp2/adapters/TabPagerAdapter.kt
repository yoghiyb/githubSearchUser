package com.yoghi.githubuserapp2.adapters

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yoghi.githubuserapp2.R
import com.yoghi.githubuserapp2.fragments.FollowerFragment
import com.yoghi.githubuserapp2.fragments.FollowingFragment

class TabPagerAdapter(private val mContext: Context, fm: FragmentManager,
                      private val username: String
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tabs_follower, R.string.tabs_following)
    }

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putString("username", username)

        when(position){
            0 -> {
                fragment = FollowerFragment()
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = bundle
                return fragment
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}