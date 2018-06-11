package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class GenerItem : RealmObject() {

    @SerializedName("queryId")
    @Expose
    open var queryId = -1

    @SerializedName("title")
    @Expose
    open var title = ""
}