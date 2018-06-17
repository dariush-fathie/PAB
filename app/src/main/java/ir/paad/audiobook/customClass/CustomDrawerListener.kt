package ir.paad.audiobook.customClass

import android.support.v4.widget.DrawerLayout
import android.transition.Slide
import android.view.View

class CustomDrawerListener(private val listener: SlideListener) : DrawerLayout.DrawerListener {

    interface SlideListener {
        fun onDrawerListener(drawerView: View, slideOffset: Float)
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        listener.onDrawerListener(drawerView, slideOffset)
    }

    override fun onDrawerOpened(drawerView: View) {

    }

    override fun onDrawerClosed(drawerView: View) {

    }

    override fun onDrawerStateChanged(newState: Int) {

    }
}
