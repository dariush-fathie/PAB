package ir.paad.audiobook.client

import ir.paad.audiobook.models.Config
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiInterface {

    @GET("update/{id}/{lat}/{lng}")
    fun updateGeoLocation(@Path("id") autoId: Int
                          , @Path("lat") lat: Double
                          , @Path("lng") lng: Double): Call<List<String>>

    @POST("getConfig/{versionCode}")
    fun getServerStatus(@Path("versionCode") versionCode: Int,
                        @Field("token") token: String,
                        @Field("userId") userId: String,
                        @Field("uuid") uuid: String,
                        @Field("deviceId") deviceId: String,
                        @Field("model") deviceModel: String): Call<Config>


}