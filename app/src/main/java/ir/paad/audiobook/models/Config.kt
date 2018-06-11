package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Config : RealmObject() {

    @SerializedName("serverStatus")
    @Expose
    open var serverStatus: Boolean = false

    @SerializedName("currentVersion")
    @Expose
    open var currentVersion: String = ""

    @SerializedName("template")
    @Expose
    open var template: Template? = null

    @SerializedName("updateForce")
    @Expose
    open var updateForce: Boolean = false

    @SerializedName("message")
    @Expose
    open var message: String = ""

    @SerializedName("updateMessage")
    @Expose
    open var updateMessage: String = ""

    @SerializedName("secret")
    @Expose
    open var userSecret: UserSecret? = null

}

