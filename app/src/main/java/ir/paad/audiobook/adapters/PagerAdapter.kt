package ir.paad.audiobook.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ir.paad.audiobook.fragments.ProfileFragment
import ir.paad.audiobook.fragments.myLibraryFragments.MyLibraryAllFragment
import ir.paad.audiobook.fragments.myLibraryFragments.MyLibraryFavFragment
import ir.paad.audiobook.fragments.myLibraryFragments.MyLibraryPurchasedFragment

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyLibraryAllFragment()
            1-> MyLibraryPurchasedFragment()
            2-> MyLibraryFavFragment()
            else -> {
                ProfileFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "همه"
            1 -> "خریداری شده"
            2 -> "نشان شده"
            else -> ""
        }
    }
}
