package ir.paad.audiobook.fragments.homeFragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import ir.paad.audiobook.R
import ir.paad.audiobook.adapters.MainAdapter
import ir.paad.audiobook.adapters.SimpleAdapter
import ir.paad.audiobook.customClass.CenterZoomLayoutManager
import ir.paad.audiobook.customClass.CustomSnapHelper
import ir.paad.audiobook.decoration.VerticalLinearLayoutDecoration
import ir.paad.audiobook.interfaces.OnItemClick
import ir.paad.audiobook.models.BookModel
import ir.paad.audiobook.utils.BooksDownloader
import ir.paad.audiobook.utils.Colors
import kotlinx.android.synthetic.main.fragment_home_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeListFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener, OnItemClick, TabLayout.OnTabSelectedListener {


    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        addOrRemoveColorFilter(tab!!, false)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        addOrRemoveColorFilter(tab!!, true)
        newGenerSelected(tab.position)
    }

    private fun addOrRemoveColorFilter(tab: TabLayout.Tab, addFilter: Boolean) {
        val view = tab.customView
        val text = view?.findViewById<AppCompatTextView>(R.id.tv_customTabTitle)
        val cardView = view?.findViewById<CardView>(R.id.cv_customTabContainer)
        val colors = Colors(activity as Context)

        if (addFilter) {
            view?.scaleY = 1f
            view?.scaleX = 1f

            text?.setTextColor(colors.colorPrimaryDark)
            text?.setBackgroundResource(R.drawable.empty)
            cardView?.cardElevation = 3f
            cardView?.setCardBackgroundColor(colors.white)
        } else {
            view?.scaleY = 0.9f
            view?.scaleX = 0.9f
            text?.setTextColor(colors.background2Transparent)
            text?.setBackgroundResource(R.drawable.dot_rect_back)
            cardView?.cardElevation = 0f
            cardView?.setCardBackgroundColor(colors.transparent)
        }
    }

    override fun onClick(host: String, position: Int) {
        parentFragment?.childFragmentManager
                ?.beginTransaction()
                ?.add(R.id.fl_homeContainer, HomeDetailFragment(), "homDetail")
                ?.addToBackStack("detail")
                ?.commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_toolbar -> return
            R.id.iv_backNav -> return//finish()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        val alpha = (Math.abs(verticalOffset).toFloat() / appBarLayout!!.totalScrollRange.toFloat())
        tv_title.alpha = alpha
        tv_title.scaleX = Math.max(alpha, 0.5f)
        tv_title.scaleY = Math.max(alpha, 0.5f)
        rv_generPicker.alpha = 1 - alpha
    }

    private var clickedPosition = 0
    var queryType = 0
    var title = ""
    lateinit var mainListAdapter: MainAdapter
    var snapedPosition = -1
    private var call: Call<List<BookModel>>? = null

    // gener picker recyclerView
    private lateinit var snapHelper: CustomSnapHelper
    private lateinit var generLayoutManager: CenterZoomLayoutManager
    private lateinit var generAdapter: SimpleAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
        //loadGenerPicker()
        // todo get  from arguments
        /*if (intent != null) {
            queryType = intent.getIntExtra(resources.getString(R.string.queryType), -1)
            title = intent.getStringExtra(resources.getString(R.string.title))
            clickedPosition = intent.getIntExtra(getString(R.string.position), 0)
        }*/
        //scrollToGenerPosition(clickedPosition)

        tbl_homeList?.addTab(tbl_homeList.newTab()
                .setText("جدیدترین"), false)
        tbl_homeList?.addTab(tbl_homeList.newTab()
                .setText("پربازدیدترین"), false)
        tbl_homeList?.addTab(tbl_homeList.newTab()
                .setText("بهترین"), false)

        tbl_homeList?.addTab(tbl_homeList.newTab()
                .setText("پیشنهاد های امروز برای شما"), false)
        tbl_homeList?.addTab(tbl_homeList.newTab()
                .setText("مجموعه ای از عناوین رایگان"), false)
        tbl_homeList?.addTab(tbl_homeList.newTab()
                .setText("با 30 درصد تخقیف بخیرید"), false)

        tbl_homeList.addOnTabSelectedListener(this)
        abl1.addOnOffsetChangedListener(this)

        Handler().postDelayed({
            tbl_homeList.getTabAt(3)?.select()
        }, 100)

    }


    private fun setClickListener() {
        rl_toolbar.setOnClickListener(this)
        iv_backNav.setOnClickListener(this)
    }

    /* private fun loadGenerPicker() {
         // todo load titles from realm
         val array = ArrayList<String>()
         array.add("جدیدترین")
         array.add("پر بازدیدترین")
         array.add("بهترین")
         array.add("پرفروش ترین")
         array.add("برای شما")

         generLayoutManager = CenterZoomLayoutManager(activity as Context, LinearLayoutManager.HORIZONTAL, false)
         generAdapter = SimpleAdapter(activity as Context, array)
         snapHelper = CustomSnapHelper()

         rv_generPicker.layoutManager = generLayoutManager
         rv_generPicker.adapter = generAdapter
         snapHelper.attachToRecyclerView(rv_generPicker)
         generAdapter.setOnItemClickListener(object : OnItemClick {
             override fun onClick(host: String, position: Int) {
                 val view = generLayoutManager.findViewByPosition(position)
                 if (view == null) {
                     rv_generPicker.smoothScrollToPosition(position)
                 } else {
                     val a = snapHelper.calculateDistanceToFinalSnap(generLayoutManager, view)
                     rv_generPicker.smoothScrollBy(a!![0], 0)
                 }

             }
         })
         rv_generPicker.addItemDecoration(CenterItemDecoration(activity as Context,
                 16,
                 8,
                 16,
                 8))

         rv_generPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {

             override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                 super.onScrollStateChanged(recyclerView, newState)
                 if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                     val centerView = snapHelper.findSnapView(generLayoutManager)
                     val newSnapPosition = generLayoutManager.getPosition(centerView)
                     if (newSnapPosition != snapedPosition) {
                         snapedPosition = newSnapPosition
                         if (snapedPosition != -1) {
                             newGenerSelected(snapedPosition)
                         }
                     }
                     // todo get title from realm
                     tv_title.text = array[snapedPosition]
                 } else {
                     //snapedPosition = -1
                     tv_title.text = ""
                 }
             }
         })
     }
 */

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createRevealAnimation() {
        val startRadius = 0
        val endRadius = Math.hypot(rv_list.width.toDouble(), rv_list.height.toDouble()).toInt()
        val anim = ViewAnimationUtils.createCircularReveal(rv_list, 0, 0, 0f, endRadius.toFloat())
        anim.duration = 500
        anim.interpolator = AccelerateInterpolator()
        anim.start()
    }


    private fun newGenerSelected(generPosition: Int) {
        // first remove current adapter
        resetMainAdapter()
        // if there is request to another genre or downloadMore cancel it
        cancelRequest()
        // now you can download items
        download()
    }

    // post scroll to position with delay
    /*private fun scrollToGenerPosition(position: Int) {
        Handler().postDelayed({
            (activity as MainActivity).runOnUiThread {
                val view = rv_generPicker.layoutManager.findViewByPosition(position)
                if (view == null) {
                    rv_generPicker.smoothScrollToPosition(position)
                } else {
                    val a = snapHelper.calculateDistanceToFinalSnap(generLayoutManager, view)
                    if (a!![0] == 0) {
                        Log.e("0 distance", "a")
                        // recursive call
                        scrollToGenerPosition(position)
                    } else {
                        rv_generPicker.smoothScrollBy(a[0], 0)
                    }
                }
            }
        }, 100)
    }*/

    private var downloading = false

    // todo set query type base on selected gener
    private fun download() {
        downloading = true
        BooksDownloader.DownloadBuilder()
                .queryType(queryType)
                .download(object : Callback<List<BookModel>> {
                    override fun onResponse(call: Call<List<BookModel>>?, response: Response<List<BookModel>>?) {

                        response ?: onFailure(call, Throwable("null response"))
                        response ?: return

                        response.body() ?: onFailure(call, Throwable("null array"))
                        response.body() ?: return
                        Log.e("onResponse", "${response.body()?.size}")
                        loadMainAdapter(ArrayList(response.body()))
                        downloading = false
                    }

                    override fun onFailure(call: Call<List<BookModel>>?, t: Throwable?) {
                        Log.e("onFailure", t?.message)
                        downloading = false
                    }
                })
    }

    private fun downloadMore() {
        downloading = true
        if (!downloading) {
            call = BooksDownloader.DownloadBuilder()
                    .offset(rv_list.adapter.itemCount)
                    .queryType(queryType)
                    .download(object : Callback<List<BookModel>> {
                        override fun onResponse(call: Call<List<BookModel>>?, response: Response<List<BookModel>>?) {
                            response ?: onFailure(call, Throwable("null response"))
                            response ?: return

                            response.body() ?: onFailure(call, Throwable("null array"))
                            response.body() ?: return

                            mainListAdapter.notifyNewItemAdded(ArrayList(response.body()))
                            downloading = false
                        }

                        override fun onFailure(call: Call<List<BookModel>>?, t: Throwable?) {
                            downloading = false
                        }


                    })
        }
    }

    private fun loadMainAdapter(books: ArrayList<BookModel>) {
        rv_list.layoutManager = LinearLayoutManager(activity as Context)
        removeDecorations()
        rv_list.addItemDecoration(VerticalLinearLayoutDecoration(activity as Context,
                4,
                4,
                4,
                4))
        mainListAdapter = MainAdapter(context = activity as Context, books = books).setOnItemClickListener(this)
        rv_list.adapter = mainListAdapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createRevealAnimation()
        }
    }

    // prevent from add two or more decoration to mainList
    private fun removeDecorations() {
        while (rv_list.itemDecorationCount > 0) {
            rv_list.removeItemDecorationAt(0)
        }
    }

    private fun resetMainAdapter() {
        rv_list.adapter = null
    }

    private fun cancelRequest() {
        call ?: return
        if (call != null) {
            if (!call?.isCanceled!!) {
                call?.cancel()
                call = null
            }
        }
    }

}
