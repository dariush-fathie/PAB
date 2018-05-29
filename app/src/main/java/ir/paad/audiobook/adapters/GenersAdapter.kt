package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R

class GenersAdapter(ctx: Context) : RecyclerView.Adapter<GenersAdapter.SlideHolder>() {

    private val context = ctx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_geners, parent, false)
        return SlideHolder(item)
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return 5
    }


    inner class SlideHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
