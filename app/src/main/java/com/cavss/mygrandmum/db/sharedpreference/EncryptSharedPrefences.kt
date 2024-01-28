package com.cavss.mygrandmum.db.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.cavss.mygrandmum.util.secure.AESHelper


class EncryptSharedPrefences(context : Context) {

//    val encryptText = AESHelper.encrypt("암호화 할 텍스트".toByteArray(Charsets.UTF_8))
//    val decryptText = String(AESHelper.decrypt(encryptText)!!, Charsets.UTF_8)
//    Log.e("mDebug", "암호화 된 텍스트 : ${encryptText}")
//    Log.e("mDebug", "복호화 된 텍스트 : ${decryptText}")

    private val preference_name = "facebook"
    private var sharedPreferences = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE)

    fun readData(key : String, value : String) : String?{
        return sharedPreferences.getString(key, value)
    }
    fun createAndUpdateData(key : String, value : String){
        sharedPreferences.edit().putString(key, value).apply()
    }
    fun deleteData(key : String){
        sharedPreferences.edit().remove(key).commit()
    }

    fun getAllData() : Map<String, *>{
        return sharedPreferences.all
    }


}