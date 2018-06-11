package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Template : RealmObject() {

    @SerializedName("slides")
    @Expose
    open var slides: RealmList<SlideItem>? = null

    @SerializedName("geners")
    @Expose
    open var geners: RealmList<GenerItem>? = null

}