package ir.paad.audiobook

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import ir.paad.audiobook.customClass.BadgeDrawable
import ir.paad.audiobook.fragments.HomeFragment
import ir.paad.audiobook.fragments.MyLibraryFragment
import ir.paad.audiobook.fragments.ProfileFragment
import ir.paad.audiobook.fragments.ShoppingCartFragment
import ir.paad.audiobook.utils.Colors
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_view_sheet.*


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {


    // default selection is home tab at position 0
    val selectedTab = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        startActivity(Intent(this@MainActivity, SplashActivity::class.java))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tbl_main.addOnTabSelectedListener(this)
        // add [HomeFragment] to backStack
        loadFragments(0)

        addBadge(0)

    }


    private fun loadFragments(position: Int) {
        popUpBackStack()
        when (position) {
            0 -> {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    Log.e("sdfs", "$position")
                    supportFragmentManager.beginTransaction()
                            .add(R.id.fl_mainContainer, HomeFragment())
                            .addToBackStack("home")
                            .commit()
                }
                return
            }
            1 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, MyLibraryFragment())
                        .addToBackStack("myLib")
                        .commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, ShoppingCartFragment())
                        .addToBackStack("shop")
                        .commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, ProfileFragment())
                        .addToBackStack("profile")
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

    override fun onTabReselected(tab: TabLayout.Tab?) {
        Log.e("tab", "reselect")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        Log.e("tab", "unselect")
        addOrRemoveColorFilter(tab!!, false)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.e("tab", "select")
        addOrRemoveColorFilter(tab!!, true)
        loadFragments(tab.position)

        addBadge(0)

    }

    private fun addOrRemoveColorFilter(tab: TabLayout.Tab, addFilter: Boolean) {
        val view = tab.customView
        val text = view?.findViewById<AppCompatTextView>(R.id.text1)
        val icon = view?.findViewById<AppCompatImageView>(R.id.icon)
        val colors = Colors(this@MainActivity)

        if (addFilter) {
            text?.setTextColor(colors.colorAccent)
            icon?.setColorFilter(colors.colorAccent)
        } else {
            text?.setTextColor(colors.white)
            icon?.setColorFilter(colors.white)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
            tbl_main.getTabAt(0)?.select()
        } else {
            // todo show exit dialog
            finish()
        }
    }

    fun addBadge(tabPosition: Int) {
        tabBadgeImageViewAtPosition(tabPosition).setImageDrawable(createBadgeDrawable(5))
    }

    fun updateBadge(tabPosition: Int, newCount: Int) {
        tabBadgeImageViewAtPosition(tabPosition).setImageDrawable(createBadgeDrawable(newCount))
    }

    fun removeBadge(tabPosition: Int) {
        tabBadgeImageViewAtPosition(tabPosition).setImageDrawable(null)
    }

    fun createBadgeDrawable(number: Int): Drawable {
        return BadgeDrawable.Builder(this)
                .type(BadgeDrawable.TYPE_NUMBER)
                .strokeWidth(2)
                .number(number)
                .build()
    }

    private fun tabBadgeImageViewAtPosition(position: Int): AppCompatImageView {
        val tabView = tbl_main.getTabAt(position)?.customView
        val badgeView = tabView?.findViewById<AppCompatImageView>(R.id.iv_tbl_badge)
        return badgeView!!
    }

}
