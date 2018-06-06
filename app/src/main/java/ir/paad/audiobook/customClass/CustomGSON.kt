package ir.paad.audiobook.customClass

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder

class CustomGSON {

    fun getGSONWithCustomStrategies() = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }

        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            //return f?.declaredClass == RealmObject::class.java
            return true  // todo remove this line
        }

    }).create()!!

}
