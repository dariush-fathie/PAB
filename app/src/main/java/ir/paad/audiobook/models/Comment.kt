package ir.paad.audiobook.models

import java.util.*

open class Comment {

    open var id:Int= -1

    open var userId:Int = -1

    open var username:String = ""

    open var avatarUrl:String = ""

    open var text:String = ""

    open var data:String = ""

    open var likeCount:Int = -1

}