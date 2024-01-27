package com.cavss.mygrandmum.ui.screen.callbook

data class CallBookModel (
    val id: Int,
    val name: String,
    val relation: String,
    val imagePath: String,
    val digit : String
){
    fun toJSON(): String {
        return "{'id':${id},'name':'${name}','relation':'${relation}','imagePath':'${imagePath}','digit':'${digit}'}"
    }
}