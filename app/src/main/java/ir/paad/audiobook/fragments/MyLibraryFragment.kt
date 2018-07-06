package ir.paad.audiobook.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.decoration.VerticalThreeColumnGridDecoration
import kotlinx.android.synthetic.main.fragment_my_library.*


class MyLibraryFragment : Fragment(), TabLayout.OnTabSelectedListener {

    override fun onTabReselected(tab: TabLayout.Tab?) {
        loadList(tab?.position!!)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        loadList(tab?.position!!)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

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
        tbl_myLib.addOnTabSelectedListener(this)
        Handler().postDelayed({
            tbl_myLib.getTabAt(0)?.select()
        }, 150)
    }


    private fun loadList(position: Int) {
        rv_myLib.layoutManager = GridLayoutManager(activity, 3)
        removeDecors()
        rv_myLib.addItemDecoration(VerticalThreeColumnGridDecoration(activity as Context,
                8,
                8,
                8,
                8))
        rv_myLib.adapter = GridAdapter()
    }

    private fun removeDecors() {
        while (rv_myLib.itemDecorationCount > 0) {
            rv_myLib.removeItemDecorationAt(0)
        }
    }

    inner class GridAdapter : RecyclerView.Adapter<GridAdapter.GridHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridHolder {
            val gridItem = LayoutInflater.from(activity).inflate(R.layout.item_grid_two_col, parent, false)
            return GridHolder(gridItem)
        }

        override fun getItemCount() = 10

        override fun onBindViewHolder(holder: GridHolder, position: Int) {

        }


        inner class GridHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

    }


}