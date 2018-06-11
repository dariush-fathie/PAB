package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class SlideItem : RealmObject() {

    @SerializedName("url")
    @Expose
    open var url = ""

    @SerializedName("type")
    @Expose
    open var type = -1

    @SerializedName("id")
    @Expose
    open var id = -1
}