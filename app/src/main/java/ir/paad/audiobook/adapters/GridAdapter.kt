package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.utils.Converter

class GridAdapter(ctx: Context) : RecyclerView.Adapter<GridAdapter.GridItemHolder>() {

    private val context = ctx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_grid, parent, false)
        return GridItemHolder(item)
    }

    override fun onBindViewHolder(holder: GridItemHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return 6
    }


    inner class GridItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}
