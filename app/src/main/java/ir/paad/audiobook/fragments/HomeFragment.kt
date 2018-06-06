package ir.paad.audiobook.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.adapters.GenersAdapter
import ir.paad.audiobook.adapters.MainAdapter
import ir.paad.audiobook.adapters.SliderAdapter
import ir.paad.audiobook.decoration.HorizontalLinearLayoutDecoration
import ir.paad.audiobook.decoration.VerticalLinearLayoutDecoration
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    /*override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (Math.abs(verticalOffset) == appBarLayout?.totalScrollRange) {
            view_gradient.visibility = View.VISIBLE
        } else {
            view_gradient.visibility = View.GONE
        }
    }
*/

    companion object {
        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSliderAdapter()
        loadGenesAdapter()
        loadMainAdapter()
    }


    private fun loadSliderAdapter() {
        rv_slider.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_slider.addItemDecoration(HorizontalLinearLayoutDecoration(activity as Context,
                8,
                16,
                8,
                16))
        rv_slider.adapter = SliderAdapter(activity as Context)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv_slider)
    }

    private fun loadGenesAdapter() {
        rv_geners.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_geners.addItemDecoration(HorizontalLinearLayoutDecoration(activity as Context,
                8,
                8,
                8,
                8))
        rv_geners.adapter = GenersAdapter(activity as Context)
    }

    private fun loadMainAdapter() {
        rv_main.layoutManager = LinearLayoutManager(activity)
        rv_main.addItemDecoration(VerticalLinearLayoutDecoration(activity as Context,
                8,
                8,
                8,
                8))
        rv_main.adapter = MainAdapter(activity as Context)
    }


}