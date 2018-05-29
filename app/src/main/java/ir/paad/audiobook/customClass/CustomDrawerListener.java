package ir.paad.audiobook.customClass;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.transition.Slide;
import android.view.View;

public class CustomDrawerListener implements DrawerLayout.DrawerListener {

    public interface SlideListener{
        void onDrawerListener(View drawerView , float slideOffset);
    }

    private SlideListener listener;

    public CustomDrawerListener(SlideListener listener){
        this.listener = listener ;
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        listener.onDrawerListener(drawerView , slideOffset);
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
