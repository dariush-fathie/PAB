package ir.paad.audiobook.fragments.myLibraryFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.decoration.VerticalThreeColumnGridDecoration
import ir.paad.audiobook.decoration.VerticalTwoColumnGridDecoration
import kotlinx.android.synthetic.main.fragment_all.*

class MyLibraryPurchasedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_all.layoutManager = GridLayoutManager(activity, 3)
        rv_all.addItemDecoration(VerticalThreeColumnGridDecoration(activity as Context,
                8,
                8,
                8,
                8))
        rv_all.adapter = GridAdapter()
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