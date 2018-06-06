package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class User : RealmObject() {

    @SerializedName("")
    @Expose
    open var username: String = ""

    @SerializedName("")
    @Expose
    open var hashPassword: String = ""

    @SerializedName("")
    @Expose
    open var wallet: Int = -1

    @SerializedName("")
    @Expose
    open var likedTags: String = ""

    @SerializedName("")
    @Expose
    open var encryptedTel: String = ""

    @SerializedName("")
    @Expose
    open var avatarUrl: String = ""

    open var userSecret: UserSecret? = null

}