package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.utils.Converter

class MainAdapter(ctx: Context) : RecyclerView.Adapter<MainAdapter.MainItemHolder>() {

    private val context = ctx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MainItemHolder(item)
    }

    override fun onBindViewHolder(holder: MainItemHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return 15
    }


    inner class MainItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
