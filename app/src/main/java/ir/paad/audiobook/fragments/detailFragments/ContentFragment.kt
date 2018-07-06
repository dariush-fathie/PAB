package ir.paad.audiobook.fragments.detailFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.adapters.ContentAdapter
import ir.paad.audiobook.decoration.VerticalLinearLayoutDecoration
import ir.paad.audiobook.fragments.MyLibraryFragment
import ir.paad.audiobook.models.TrackModel
import kotlinx.android.synthetic.main.fragment_detail_content.*


class ContentFragment : Fragment() {


    companion object {
        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_content, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_detailContent.layoutManager = LinearLayoutManager(activity as Context)
        rv_detailContent.addItemDecoration(VerticalLinearLayoutDecoration(activity as Context,
                8,
                8,
                8,
                8))

        rv_detailContent.adapter = ContentAdapter(activity as Context, ArrayList<TrackModel>())

    }

}