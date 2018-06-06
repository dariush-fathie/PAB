package ir.paad.audiobook.models

open class PurchasedProduct{
    open var productId:Int = -1

    open var trackId:Int = -1

    open var trackNo:Int = -1

    open var user:String = ""

    open var secretKey:String = ""

    open var isFree:Boolean = false
}
