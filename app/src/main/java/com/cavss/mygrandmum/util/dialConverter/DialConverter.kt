package com.cavss.mygrandmum.util.dialConverter

object DialConverter {

    fun String.toPhoneNumber() : String {

        val getFirstNumbers = this.substring(0, 3)
        val getSecondNumbers = this.substring(3,7)
        val getThirdNumbers = this.substring(7)

        return "tel:${getFirstNumbers}-${getSecondNumbers}-${getThirdNumbers}"
    }
}