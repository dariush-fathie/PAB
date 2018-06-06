package ir.paad.audiobook.client

import ir.paad.audiobook.customClass.CustomGSON
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val baseUrl = "http://services.taknobat.ir/"
    private var retrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(CustomGSON().getGSONWithCustomStrategies()))
                        .build()
            }
            return retrofit!!
        }

}