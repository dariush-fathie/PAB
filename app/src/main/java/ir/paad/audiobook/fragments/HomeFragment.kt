package ir.paad.audiobook.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import ir.paad.audiobook.R
import ir.paad.audiobook.adapters.GenersAdapter
import ir.paad.audiobook.adapters.MainAdapter
import ir.paad.audiobook.adapters.SliderAdapter
import ir.paad.audiobook.decoration.HorizontalLinearLayoutDecoration
import ir.paad.audiobook.decoration.VerticalLinearLayoutDecoration
import ir.paad.audiobook.models.BookModel
import ir.paad.audiobook.models.GenerItem
import ir.paad.audiobook.models.SlideItem
import ir.paad.audiobook.models.Template
import ir.paad.audiobook.utils.BooksDownloader
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    companion object {

        val isLive = true

        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadTemplate()
        setClickListeners()
    }


    private fun setClickListeners() {
        iv_menu.setOnClickListener(this)
        iv_sync.setOnClickListener(this)
    }

    private fun loadTemplate() {

        val realm = Realm.getDefaultInstance()
        var template: Template? = null
        realm.executeTransaction { db ->
            // there is only one instance of Template in database
            template = db.where(Template::class.java).findFirst()
        }

        template ?: throw Exception("template is null check your code again honey!")

        if (template?.slides == null) {
            Log.e("template", "slides array is null(empty)")
        } else {
            loadSliderAdapter(template?.slides?.toTypedArray()!!)
        }

        if (template?.geners == null) {
            Log.e("template", "geners array is null(empty)")
        } else {
            loadGenersAdapter(template?.geners?.toTypedArray()!!)
        }

        BooksDownloader.DownloadBuilder()
                .offset(0)
                .maxCount(30)
                .queryType(-1)
                .sortType(BooksDownloader.SortType.ASCEND)
                .download(object : Callback<List<BookModel>> {
                    override fun onResponse(call: Call<List<BookModel>>?, response: Response<List<BookModel>>?) {

                        response ?: onFailure(call, Throwable("null response"))
                        response ?: return

                        response.body() ?: onFailure(call, Throwable("null array"))
                        response.body() ?: return

                        loadMainAdapter(ArrayList(response.body()))
                    }

                    override fun onFailure(call: Call<List<BookModel>>?, t: Throwable?) {
                        Log.e("BooksDownloader", t?.message)
                    }
                })


    }

    private fun loadSliderAdapter(slides: Array<SlideItem>) {
        rv_slider.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        rv_slider.addItemDecoration(HorizontalLinearLayoutDecoration(activity as Context,
                8,
                8,
                8,
                8
        ))

        rv_slider.adapter = SliderAdapter(context = activity as Context, slides = slides)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv_slider)
    }

    private fun loadGenersAdapter(geners: Array<GenerItem>) {
        rv_geners.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_geners.addItemDecoration(HorizontalLinearLayoutDecoration(activity as Context,
                8,
                8,
                8,
                8))
        rv_geners.adapter = GenersAdapter(context = activity as Context, geners = geners)

    }

    private fun loadMainAdapter(books: ArrayList<BookModel>) {
        rv_main.layoutManager = LinearLayoutManager(activity)
        rv_main.addItemDecoration(VerticalLinearLayoutDecoration(activity as Context,
                8,
                8,
                8,
                8))
        rv_main.adapter = MainAdapter(context = activity as Context, books = books)
    }

}