package ir.paad.audiobook.fragments.homeFragments

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.fragments.detailFragments.CommentFragment
import ir.paad.audiobook.fragments.detailFragments.ContentFragment
import ir.paad.audiobook.fragments.detailFragments.IntroductionFragment
import ir.paad.audiobook.utils.Colors
import kotlinx.android.synthetic.main.fragment_home_detail.*


class HomeDetailFragment : Fragment(), TabLayout.OnTabSelectedListener, AppBarLayout.OnOffsetChangedListener, View.OnClickListener, FragmentManager.OnBackStackChangedListener {
    override fun onBackStackChanged() {
        Log.e("detailFragment", "${childFragmentManager.backStackEntryCount}")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_backNav -> {
                //finish()
            }
            R.id.iv_mark -> {
                mark()
            }
            R.id.btn_buy -> {
                tbl_detail.getTabAt(0)?.select()
                abl_detail.setExpanded(false, true)
            }
        }
    }

    private var isMarked = false

    private fun mark() {
        if (isMarked) {
            iv_mark.clearColorFilter()
            iv_mark.setImageResource(R.drawable.ic_bookmark)
        } else {
            iv_mark.setColorFilter(Colors(activity as Context).colorAccent, PorterDuff.Mode.SRC_IN)
            iv_mark.setImageResource(R.drawable.ic_bookmark_filled)
        }
        isMarked = !isMarked
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (Math.abs(verticalOffset) == appBarLayout?.totalScrollRange) {
            view_gradient.visibility = View.VISIBLE
        } else {
            view_gradient.visibility = View.GONE
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        tintFabPlay()
        tbl_detail.addOnTabSelectedListener(this)
        tbl_detail.getTabAt(2)?.select()
        abl_detail.addOnOffsetChangedListener(this)
        childFragmentManager.addOnBackStackChangedListener(this)
    }


    private fun setListeners() {
        iv_backNav.setOnClickListener(this)
        iv_mark.setOnClickListener(this)
        btn_buy.setOnClickListener(this)
    }

    private fun tintFabPlay() {
        var drawable = fab_play.drawable
        drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, Colors(activity as Context).colorPrimary)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        loadFragments(tab?.position!!)
    }

    private fun loadFragments(position: Int) {
        popUpBackStack()
        when (position) {
            2 -> {
                if (childFragmentManager.backStackEntryCount == 0) {
                    Log.e("sdfs", "$position")
                    childFragmentManager.beginTransaction()
                            .add(R.id.fl_detailContainer, IntroductionFragment())
                            .addToBackStack("intro")
                            .commit()
                }
                return
            }
            1 -> {
                childFragmentManager.beginTransaction()
                        .add(R.id.fl_detailContainer, CommentFragment())
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .addToBackStack("comment")
                        .commit()
            }

            0 -> {
                childFragmentManager.beginTransaction()
                        .add(R.id.fl_detailContainer, ContentFragment())
                        .addToBackStack("content")
                        .commit()
            }
        }
        Log.e("backStackCount", "${childFragmentManager.backStackEntryCount}#")
    }

    private fun popUpBackStack() {
        while (childFragmentManager.backStackEntryCount > 1) {
            childFragmentManager.popBackStackImmediate()
        }
    }

}

