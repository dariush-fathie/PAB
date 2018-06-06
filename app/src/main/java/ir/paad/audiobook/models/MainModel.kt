package ir.paad.audiobook.models

import com.google.gson.annotations.SerializedName

open class MainModel {

    @SerializedName("")
    open var id: Int = -1

    @SerializedName("")
    open var name: String = ""

    @SerializedName("")
    open var authorId: Int = -1

    @SerializedName("")
    open var authorName: String = "" // todo : add this field to database

    @SerializedName("")
    open var imageUrl: String = ""

    @SerializedName("")
    open var tags: String = ""

    @SerializedName("")
    open var ageLimit = ""

    @SerializedName("")
    open var viewCount: Int = -1

    @SerializedName("")
    open var publisher: String = ""

    @SerializedName("")
    open var sampleTrack: String = ""

    @SerializedName("")
    open var likeAverage: Int = 0

    @SerializedName("")
    open var publishYear: String = ""

    @SerializedName("")
    open var duration: Int = 0

    @SerializedName("")
    open var translator: String = ""

    @SerializedName("")
    open var speakers: String = ""

    @SerializedName("")
    open var foreign: Boolean = false

}