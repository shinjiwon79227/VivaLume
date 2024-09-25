package com.sjw.vivalume

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class WiseSayingManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("WiseSaying", Context.MODE_PRIVATE)

    // 명언 리스트를 SharedPreferences에 저장하는 함수
    fun saveWiseSayings(wiseSayings: List<WiseSaying>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(wiseSayings)
        editor.putString("wiseSaying", json) // 키 이름은 "wiseSaying"로 유지
        editor.apply()
    }

    // SharedPreferences에서 명언 리스트를 불러오는 함수
    fun getWiseSayings(): List<WiseSaying>? {
        val json = sharedPreferences.getString("wiseSaying", null) // "wiseSaying"에서 데이터 가져오기
        return if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<List<WiseSaying>>() {}.type
            gson.fromJson(json, type)
        } else {
            null
        }
    }

    // 랜덤으로 명언을 선택하는 함수
    fun getRandomWiseSaying(): WiseSaying? {
        val wiseSayings = getWiseSayings()
        return wiseSayings?.let {
            it[Random.nextInt(it.size)] // 랜덤 인덱스로 명언 선택
        }
    }
}
