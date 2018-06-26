package ir.paad.audiobook.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ir.paad.audiobook.fragments.detailFragments.CommentFragment
import ir.paad.audiobook.fragments.detailFragments.ContentFragment
import ir.paad.audiobook.fragments.detailFragments.IntroductionFragment

class DetailPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> ContentFragment()
            1 -> CommentFragment()
            2 -> IntroductionFragment()
            else -> null
        }

    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "محتویات"
            1 -> "نظرات"
            2 -> "معرفی"
            else -> ""
        }
    }
}
