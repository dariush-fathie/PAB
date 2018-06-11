package ir.paad.audiobook.client

import ir.paad.audiobook.models.Config
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @GET("update/{userId}/{lat}/{lng}")
    fun updateGeoLocation(@Path("userId") autoId: Int
                          , @Path("lat") lat: Double
                          , @Path("lng") lng: Double): Call<List<String>>

    @FormUrlEncoded
    @POST("getConfig")
    fun getServerStatus(@Field("versionCode") versionCode: Int,
                        @Field("token") token: String,
                        @Field("userId") userId: String,
                        @Field("uuidExist") uuidExist: Boolean,
                        @Field("uuid") uuid: String,
                        @Field("deviceId") deviceId: String,
                        @Field("deviceModel") deviceModel: String,
                        @Field("ft") firstTime: Boolean): Call<Config>

}