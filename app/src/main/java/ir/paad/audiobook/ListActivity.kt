package ir.paad.audiobook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import ir.paad.audiobook.adapters.MainAdapter
import ir.paad.audiobook.decoration.VerticalLinearLayoutDecoration
import ir.paad.audiobook.models.BookModel
import ir.paad.audiobook.utils.BooksDownloader
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity() {

    var queryType = -1
    var title = ""
    lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (intent != null) {
            queryType = intent.getIntExtra(resources.getString(R.string.queryType), -1)
            title = intent.getStringExtra(resources.getString(R.string.title))
        }

        BooksDownloader.DownloadBuilder()
                .queryType(queryType)
                .download(object : Callback<List<BookModel>> {
                    override fun onResponse(call: Call<List<BookModel>>?, response: Response<List<BookModel>>?) {

                        response ?: onFailure(call, Throwable("null response"))
                        response ?: return

                        response.body() ?: onFailure(call, Throwable("null array"))
                        response.body() ?: return

                        loadAdapter(ArrayList(response.body()))

                    }

                    override fun onFailure(call: Call<List<BookModel>>?, t: Throwable?) {

                    }
                })
    }


    private fun loadAdapter(books: ArrayList<BookModel>) {
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.addItemDecoration(VerticalLinearLayoutDecoration(this,
                8,
                8,
                8,
                8))
        adapter = MainAdapter(context = this, books = books)
        rv_list.adapter = adapter
    }

    private var downloading = false

    private fun loadMore() {
        downloading = true
        if (!downloading) {
            BooksDownloader.DownloadBuilder()
                    .offset(rv_list.adapter.itemCount)
                    .queryType(queryType)
                    .download(object : Callback<List<BookModel>> {
                        override fun onResponse(call: Call<List<BookModel>>?, response: Response<List<BookModel>>?) {
                            response ?: onFailure(call, Throwable("null response"))
                            response ?: return

                            response.body() ?: onFailure(call, Throwable("null array"))
                            response.body() ?: return

                            adapter.notifyNewItemAdded(ArrayList(response.body()))
                            downloading = false
                        }

                        override fun onFailure(call: Call<List<BookModel>>?, t: Throwable?) {
                            downloading = false
                        }

                    })
        }
    }


}
