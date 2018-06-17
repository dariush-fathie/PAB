package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class BookModel : RealmObject() {

    @SerializedName("proId")
    @Expose
    open var id: Int = -1

    @SerializedName("proName")
    @Expose
    open var name: String = ""

    @SerializedName("authorId")
    @Expose
    open var authorId: Int = -1

    @SerializedName("intro")
    @Expose
    open var introduction = ""

    @SerializedName("authorName")
    @Expose
    open var authorName: String = "" // todo : add this field to database

    @SerializedName("photoUri")
    @Expose
    open var imageUrl: String = ""

    @SerializedName("tags")
    @Expose
    open var tags: String = ""

    @SerializedName("ageLimit")
    @Expose
    open var ageLimit = ""

    @SerializedName("viewCount")
    @Expose
    open var viewCount: Int = -1

    @SerializedName("publisher")
    @Expose
    open var publisher: String = ""

    @SerializedName("sampleTrack")
    @Expose
    open var sampleTrack: String = ""

    @SerializedName("likeAverage")
    @Expose
    open var likeAverage: Int = 0

    @SerializedName("publishYear")
    @Expose
    open var publishYear: String = ""

    @SerializedName("duration")
    @Expose
    open var duration: Int = 0

    @SerializedName("translator")
    @Expose
    open var translators: String = ""

    @SerializedName("speaker")
    @Expose
    open var speakers: String = ""

    @SerializedName("foreign_")
    @Expose
    open var foreign: Boolean = false

    @SerializedName("tracks")
    @Expose
    open var tracksList: RealmList<TrackModel>? = null

}