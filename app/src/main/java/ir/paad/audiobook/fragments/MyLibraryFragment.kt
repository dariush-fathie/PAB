package ir.paad.audiobook.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.adapters.PagerAdapter
import kotlinx.android.synthetic.main.fragment_my_library.*


class MyLibraryFragment : Fragment() {


    companion object {
        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_library, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vp_myLib.adapter = PagerAdapter(fragmentManager!!)
        tbl_myLib.setupWithViewPager(vp_myLib)

    }

}