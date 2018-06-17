package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class TrackModel : RealmObject() {

    @SerializedName("trackId")
    @Expose
    var trackId = -1

    @SerializedName("title")
    @Expose
    var title = ""

    @SerializedName("price")
    @Expose
    var price = -1

    @SerializedName("size")
    @Expose
    var size = -1f

    @SerializedName("duration")
    @Expose
    var duration = -1

    @SerializedName("discount")
    @Expose
    var discount = 0f

}