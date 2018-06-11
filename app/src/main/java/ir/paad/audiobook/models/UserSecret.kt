package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class UserSecret : RealmObject() {

    @SerializedName("token")
    @Expose
    open var token = ""

    @SerializedName("userId")
    @Expose
    open var userId = ""

    @SerializedName("newToken")
    @Expose
    open var newToken = false

    @SerializedName("newId")
    @Expose
    open var newId = false


    override fun toString(): String {
        return "$token : $userId : $newToken : $newId"
    }

}