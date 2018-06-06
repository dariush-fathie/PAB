package ir.paad.audiobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Config {

    @SerializedName("serverStatus")
    @Expose
    open var serverStatus: Boolean = false

    @SerializedName("currentVersion")
    @Expose
    open var currentVersion: String = ""

    @SerializedName("templatePath")
    @Expose
    open var templatePath: String = ""

    @SerializedName("updateForce")
    @Expose
    open var updateForce: Boolean = false

    @SerializedName("message")
    @Expose
    open var message: String = ""

    @SerializedName("updateMessage")
    @Expose
    open var updateMessage: String = ""

    @SerializedName("updateMessage")
    @Expose
    open var userSecret: UserSecret? = null


}

