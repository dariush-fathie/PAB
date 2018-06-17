package ir.paad.audiobook.utils

import ir.paad.audiobook.client.ApiClient
import ir.paad.audiobook.client.ApiInterface
import ir.paad.audiobook.models.BookModel
import org.json.JSONObject
import retrofit2.Callback

class BooksDownloader {

    enum class SortType {
        ASCEND, DESCEND
    }

    class DownloadOption {

        // number of returned for each request
        var maxCount = -1

        var i = 0

        var offset = 0

        var sortType = SortType.ASCEND

        // default type is -1 it's get item from database with no condition
        var queryType = -1

        fun convertToJson(): String {
            val option = JSONObject()
            option.put("maxCount", maxCount)
            if (sortType == SortType.ASCEND) {
                option.put("sortType", 0)
            } else {
                option.put("sortType", 1)
            }

            option.put("queryType", queryType)
            return option.toString()
        }

    }

    class DownloadBuilder {

        private val option = DownloadOption()


        fun sortType(sort: SortType) = apply {
            option.sortType = sort
        }

        fun maxCount(maxCount: Int) = apply {
            option.maxCount = maxCount
        }

        fun offset(listSize: Int) = apply {
            option.offset = listSize
        }

        fun queryType(queryType: Int) = apply {
            option.queryType = queryType
        }


        fun download(callback: Callback<List<BookModel>>) {
            ApiClient()
                    .client
                    .create(ApiInterface::class.java)
                    .getItems(option.convertToJson())
                    .enqueue(callback)
        }
    }


}