package ir.paad.audiobook

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import ir.paad.audiobook.fragments.detailFragments.CommentFragment
import ir.paad.audiobook.fragments.detailFragments.ContentFragment
import ir.paad.audiobook.fragments.detailFragments.IntroductionFragment
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_backNav -> {
                finish()
            }
            R.id.iv_mark -> {
                mark()
            }
        }
    }

    private var isMarked = false

    private fun mark() {
        if (isMarked) {
            iv_mark.setImageResource(R.drawable.ic_bookmark)
        } else {
            iv_mark.setImageResource(R.drawable.ic_bookmark_filled)
        }
        isMarked = !isMarked
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0) {
            view_gradient.visibility = View.GONE
        } else {
            view_gradient.visibility = View.VISIBLE
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setListeners()
        tbl_detail.addOnTabSelectedListener(this)
        tbl_detail.getTabAt(2)?.select()
        abl_detail.addOnOffsetChangedListener(this)
    }


    private fun setListeners() {
        iv_backNav.setOnClickListener(this)
        iv_mark.setOnClickListener(this)
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
                if (supportFragmentManager.backStackEntryCount == 0) {
                    Log.e("sdfs", "$position")
                    supportFragmentManager.beginTransaction()
                            .add(R.id.fl_detailContainer, IntroductionFragment())
                            .addToBackStack("intro")
                            .commit()
                }
                return
            }
            1 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_detailContainer, CommentFragment())
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .addToBackStack("comment")
                        .commit()
            }

            0 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_detailContainer, ContentFragment())
                        .addToBackStack("content")
                        .commit()
            }
        }
        Log.e("backStackCount", "${supportFragmentManager.backStackEntryCount}#")
    }

    private fun popUpBackStack() {
        while (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        }
    }

}

