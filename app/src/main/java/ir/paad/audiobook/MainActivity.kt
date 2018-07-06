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
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.Base64
import android.util.Log
import android.view.View
import ir.paad.audiobook.customClass.BadgeDrawable
import ir.paad.audiobook.customClass.PlayerStateListener
import ir.paad.audiobook.fragments.*
import ir.paad.audiobook.interfaces.IOnBackPressed
import ir.paad.audiobook.models.events.PlayerEvent
import ir.paad.audiobook.services.PlayerService
import ir.paad.audiobook.utils.Colors
import ir.paad.audiobook.utils.Converter
import ir.paad.audiobook.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_view_sheet.*
import net.idik.lib.cipher.so.CipherClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, ServiceConnection, View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    override fun onBackStackChanged() {
        Log.e("mainActivity", "${supportFragmentManager.backStackEntryCount}")
        hideLoadLayout()
    }

    private lateinit var iOnBackPressed: IOnBackPressed

    fun setOnBackPressed(i: IOnBackPressed) {
        this.iOnBackPressed = i
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_bottomSheetPlayerView -> {
                onPlayerClick()
            }

            R.id.iv_closePlayer -> {
                onCloseClick()
            }
        }
    }

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var mBottomSheetCallback: BottomSheetBehavior.BottomSheetCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("cipher string", CipherClient.hello())

        tbl_main.addOnTabSelectedListener(this)

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //window.navigationBarColor = Colors(this).semiTransparent
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        */

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

        val aes = "AES/CTR/NoPadding"

        val a = arrayOf(645L)

        val data = aes.toByteArray(Charsets.UTF_8)
        val base64 = Base64.encodeToString(data, Base64.DEFAULT)

        val data1 = Base64.decode(base64, Base64.DEFAULT)
        val text = String(data, Charsets.UTF_8)


        Log.e("plain text", text)
        Log.e("encode text", base64)

        // add [HomeFragment] to backStack
        loadFragments(0)
        //addBadge(0)
        initBottomSheet()
        setListeners()

        supportFragmentManager.addOnBackStackChangedListener(this)

    }




    private fun setListeners() {
        cl_bottomSheetPlayerView.setOnClickListener(this)
        iv_closePlayer.setOnClickListener(this)
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
        ToastUtil.showShortToast(this, "player stop")
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
        exo_playerViewController.setControlDispatcher(mPlayerService.getDispatcher())

        exo_smallPlayerViewController.player = mPlayerService.player
        exo_smallPlayerViewController.setControlDispatcher(mPlayerService.getDispatcher())

        showNotificationPlayer()
        mPlayerService.player!!.addListener(PlayerStateListener(object : PlayerStateListener.OnPlayerStateChanged {
            override fun onPlay() {
                //ToastUtil.showShortToast(this@MainActivity, "onPlay")
                showSmallPlayer()
            }

            override fun onPause() {
                //ToastUtil.showShortToast(this@MainActivity, "onPause")
            }
        }, mPlayerService.player!!.playWhenReady))
        mBound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }

    private fun showNotificationPlayer() {
        mPlayerService.showNotification()
    }

    private fun onCloseClick() {
        if (this::mPlayerService.isInitialized) {
            mPlayerService.player!!.playWhenReady = false
            hideSmallPlayer()
        }
    }

    private fun onPlayerClick() {
        if (this::mPlayerService.isInitialized) {
            openBottomSheet()
        }
    }
    // end music player


    private fun hideSmallPlayer() {
        cl_bottomSheetPlayerView.visibility = View.INVISIBLE
        hideSmallPlayerShadow()
    }

    private fun showSmallPlayer() {
        cl_bottomSheetPlayerView.visibility = View.VISIBLE
        showSmallPlayerShadow()
    }

    private fun hideSmallPlayerShadow() {
        view_gradient1.visibility = View.GONE
    }

    private fun showSmallPlayerShadow() {
        view_gradient1.visibility = View.VISIBLE
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
        val tabLayoutContainerHeight = Converter.pxFromDp(this, 55f)
        mBottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                rl_smallController.alpha = 1 - slideOffset
                rl_mainController.alpha = slideOffset
                tbl_main.translationY = slideOffset * tabLayoutContainerHeight
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    rl_smallController.visibility = View.GONE
                } else {
                    rl_smallController.visibility = View.VISIBLE
                }
            }
        }
        return mBottomSheetCallback
    }

    private fun loadFragments(position: Int) {
        showLoadLayout()
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
                hideLoadLayout()
                return
            }
            1 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, SearchFragment())
                        .addToBackStack(null)
                        .commit()
            }

            2 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, MyLibraryFragment())
                        .addToBackStack(null)
                        .commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
            }
            4 -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fl_mainContainer, MoreFragment())
                        .addToBackStack(null)
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

    private fun showLoadLayout() {
        ll_load.visibility = View.VISIBLE
    }

    fun hideLoadLayout() {
        Handler().postDelayed({
            ll_load.visibility = View.GONE
        }, 250)
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
        if (mBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            closeBottomSheet()
        } else {
            if (tbl_main.selectedTabPosition == 0) {
                iOnBackPressed.backPressed()
            } else {
                if (supportFragmentManager.backStackEntryCount > 1) {
                    popUpBackStack()
                    tbl_main.getTabAt(0)?.select()
                }
            }
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

