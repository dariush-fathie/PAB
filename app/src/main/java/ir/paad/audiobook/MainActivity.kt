package ir.paad.audiobook

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.View
import com.andrognito.flashbar.Flashbar
import ir.paad.audiobook.customClass.BadgeDrawable
import ir.paad.audiobook.customClass.PlayerStateListener
import ir.paad.audiobook.fragments.HomeFragment
import ir.paad.audiobook.fragments.MyLibraryFragment
import ir.paad.audiobook.fragments.ProfileFragment
import ir.paad.audiobook.fragments.SearchFragment
import ir.paad.audiobook.models.events.PlayerEvent
import ir.paad.audiobook.services.PlayerService
import ir.paad.audiobook.utils.Colors
import ir.paad.audiobook.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_view_sheet.*
import kotlinx.android.synthetic.main.small_player_control.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe



class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, ServiceConnection, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_playPause -> {
                onPlayClick()
            }
            R.id.iv_closePlayer -> {
                onCloseClick()
            }
            R.id.cl_bottomSheetPlayerView -> {
                onPlayerClick()
            }

        }
    }


    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var mBottomSheetCallback: BottomSheetBehavior.BottomSheetCallback

    lateinit var mTbl: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mTbl = tbl_main

        tbl_main.addOnTabSelectedListener(this)

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            //w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Colors(this).semiTransparent
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }*/

        /*val decorView = window.decorView
        val uiOption = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        decorView.systemUiVisibility = uiOption

        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                // TODO: The system bars are visible. Make any desired
                // adjustments to your UI, such as showing the action bar or
                // other navigational controls.
                decorView.systemUiVisibility = uiOption
            } else {
                // TODO: The system bars are NOT visible. Make any desired
                // adjustments to your UI, such as hiding the action bar or
                // other navigational controls.
            }
        }*/

        Handler().postDelayed({
            runOnUiThread {
                Flashbar.Builder(this)
                        .gravity(Flashbar.Gravity.TOP)
                        .title("\n\n Hello World!")
                        .titleSizeInSp(18f)
                        .messageAppearance(R.style.CustomTextStyle)
                        .message("\n " + "سلام داریوش جان" +
                                "نسخه جدید برنامه منتشر شده میخوایی نصب کنی ؟" + "\n ")
                        .backgroundColorRes(R.color.colorPrimary)
                        .negativeActionText("فعلا نه")
                        .positiveActionText("چرا که نه")
                        .enableSwipeToDismiss()
                        .build()
                        .show()
            }
        }, 2500)


        // add [HomeFragment] to backStack
        loadFragments(0)
        //addBadge(0)
        initBottomSheet()
        //startService(Intent(this, PlayerService::class.java))
        setListeners()
    }

    private fun setListeners() {
        iv_playPause.setOnClickListener(this)
        iv_closePlayer.setOnClickListener(this)
        cl_bottomSheetPlayerView.setOnClickListener(this)
    }

    // start music player


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        val intent = Intent(this, PlayerService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
        startService(intent)
    }

    private var mBound = false

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        Log.e("mainActivity", "onStop")
        if (mBound) {
            unbindService(this)
            mBound = false
        }
        super.onStop()
    }


    @Subscribe
    fun onPlayerStop(playerEvent: PlayerEvent) {
        // todo player stop ... do something
        ToastUtil.showShortToast(this , "player stop")
    }

    override fun onDestroy() {
        Log.e("mainActivity", "onDestroy")
        super.onDestroy()
    }

    private lateinit var mPlayerService: PlayerService

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val b = service as PlayerService.MyBinder
        mPlayerService = b.playerService
        exo_playerViewController.player = mPlayerService.player

        changePlayerImage(mPlayerService.player!!.playWhenReady)
        showNotificationPlayer()
        mPlayerService.player!!.addListener(PlayerStateListener(object : PlayerStateListener.OnPlayerStateChanged {
            override fun onPlay() {
                //ToastUtil.showShortToast(this@MainActivity, "onPlay")
                changePlayerImage(true)
                showSmallPlayer()
            }

            override fun onPause() {
                //ToastUtil.showShortToast(this@MainActivity, "onPause")
                changePlayerImage(false)
            }
        }, mPlayerService.player!!.playWhenReady))
        mBound = true
    }


    override fun onServiceDisconnected(name: ComponentName?) {

    }

    private fun showNotificationPlayer() {
        mPlayerService.showNotification()
    }

    private fun onPlayClick() {
        if (this::mPlayerService.isInitialized) {
            if (mPlayerService.player!!.playWhenReady) {
                mPlayerService.player!!.playWhenReady = false
                changePlayerImage(false)
            } else {
                mPlayerService.play()
                changePlayerImage(true)
                showSmallPlayer()
            }
        }
    }

    private fun onCloseClick() {
        if (this::mPlayerService.isInitialized) {
            mPlayerService.player!!.playWhenReady = false
            changePlayerImage(false)
            hideSmallPlayer()
        }
    }

    private fun onPlayerClick() {
        if (this::mPlayerService.isInitialized) {
            openBottomSheet()
        }
    }
    // end music player

    private fun changePlayerImage(isPlaying: Boolean) {
        if (isPlaying) {
            iv_playPause.setImageResource(R.drawable.ic_pause_bold)
        } else {
            iv_playPause.setImageResource(R.drawable.ic_play_bold)
        }

    }

    private fun hideSmallPlayer() {
        cl_bottomSheetPlayerView.visibility = View.INVISIBLE
    }

    private fun showSmallPlayer() {
        cl_bottomSheetPlayerView.visibility = View.VISIBLE
    }

    private fun closeBottomSheet() {
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun openBottomSheet() {
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


    private fun initBottomSheet() {
        mBottomSheetBehavior = BottomSheetBehavior.from(cl_bottomSheetPlayerView)
        mBottomSheetBehavior.setBottomSheetCallback(getBottomSheetCallback())
    }

    private fun getBottomSheetCallback(): BottomSheetBehavior.BottomSheetCallback {
        mBottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                rl_smallController.alpha = 1 - slideOffset
                rl_mainController.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        }
        return mBottomSheetCallback
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
                        .add(R.id.fl_mainContainer, SearchFragment())
                        .addToBackStack("search")
                        .commit()
            }

            2 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, MyLibraryFragment())
                        .addToBackStack("myLib")
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

    }

    private fun addOrRemoveColorFilter(tab: TabLayout.Tab, addFilter: Boolean) {
        val view = tab.customView
        val text = view?.findViewById<AppCompatTextView>(R.id.text1)
        val icon = view?.findViewById<AppCompatImageView>(R.id.icon)
        val colors = Colors(this@MainActivity)

        if (addFilter) {
            text?.setTextColor(colors.white)
            icon?.setColorFilter(colors.white)
            text?.visibility = View.VISIBLE
        } else {
            text?.setTextColor(colors.hintTextColor)
            icon?.setColorFilter(colors.hintTextColor)
            text?.visibility = View.GONE
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

    private fun createBadgeDrawable(number: Int): Drawable {
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
