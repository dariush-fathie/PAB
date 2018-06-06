package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class UserSecret : RealmObject() {

    @SerializedName("")
    @Expose
    open var token = ""

    @SerializedName("")
    @Expose
    open var id = ""

    @SerializedName("")
    @Expose
    open var newToken = false

    @SerializedName("")
    @Expose
    open var newId = false
}