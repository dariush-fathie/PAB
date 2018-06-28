package ir.paad.audiobook.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.MainActivity
import ir.paad.audiobook.R
import ir.paad.audiobook.fragments.homeFragments.HomeMainFragment
import ir.paad.audiobook.interfaces.IOnBackPressed

class HomeFragment : Fragment(), View.OnClickListener, FragmentManager.OnBackStackChangedListener, IOnBackPressed {


    companion object {

        val isLive = true

        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setOnBackPressed(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager
                .beginTransaction()
                .add(R.id.fl_homeContainer, HomeMainFragment(), "homeMain")
                .addToBackStack("main")
                .commit()
        childFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun backPressed() {
        Log.e("HomeFragment", "onBackPressed")
        val topFragment = childFragmentManager.fragments[childFragmentManager.backStackEntryCount.minus(1)]
        if (childFragmentManager.backStackEntryCount == 1) {
            // todo show exit dialog
            activity?.finish()
        } else {
            childFragmentManager.popBackStackImmediate()
        }
    }


    override fun onBackStackChanged() {
        Log.e("backStackSize", "${childFragmentManager.backStackEntryCount}")
    }

    override fun onClick(v: View?) {

    }

}