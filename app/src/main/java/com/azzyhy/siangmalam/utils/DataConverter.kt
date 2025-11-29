package com.azzyhy.siangmalam.utils

import android.content.Context
import android.util.Log
import com.azzyhy.siangmalam.model.Makanan
import com.azzyhy.siangmalam.model.Minuman
import com.azzyhy.siangmalam.model.Menu
import com.azzyhy.siangmalam.model.Riwayat
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.Type

object DataConverter {

    private const val FILE_NAME = "historyTransaction.json"

    private val menuDeserializer = object : JsonDeserializer<Menu> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): Menu {
            val jsonObject = json.asJsonObject

            val type = if (jsonObject.has("tipe")) {
                jsonObject.get("tipe").asString
            } else {
                // Fallback kalau null (biar ga crash)
                "makanan"
            }

            return if (type.equals("minuman", ignoreCase = true)) {
                context.deserialize<Minuman>(json, Minuman::class.java)
            } else {
                context.deserialize<Makanan>(json, Makanan::class.java)
            }
        }
    }

    // 2. DAFTARKAN PENERJEMAH KE GSON (INI YANG KURANG TADI!)
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Menu::class.java, menuDeserializer) // <--- KUNCI RAHASIANYA DI SINI
        .create()


    // ... Fungsi saveTransactions dan loadTransactions TETAP SAMA ...

    fun saveTransactions(context: Context, data: ArrayList<Riwayat>) {
        try {
            val jsonString = gson.toJson(data)
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(jsonString.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("DataManager", "saveTransactions: error! ${e.message}")
        }
    }

    fun loadTransactions(context: Context): ArrayList<Riwayat> {
        var transaksi = ArrayList<Riwayat>()
        try {
            val jsonString = context.openFileInput(FILE_NAME).bufferedReader().use {
                it.readText()
            }
            val type = object : TypeToken<ArrayList<Riwayat>>() {}.type
            transaksi = gson.fromJson(jsonString, type)

        } catch (e: FileNotFoundException) {
            // File belum ada, wajar untuk install pertama
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("DataManager", "loadTransactions: error! ${e.message}")
        } catch (e: JsonSyntaxException) {
            // Jaga-jaga kalau format JSON rusak
            Log.e("DataManager", "JSON Error: ${e.message}")
        }
        return transaksi
    }

    fun clearAllTransactions(context: Context): Boolean {
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (file.exists()) {
                val deleted = file.delete()
                Log.d("DataManager", "File history berhasil dihapus: $deleted")
                return deleted
            } else {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("DataManager", "Gagal menghapus history: ${e.message}")
            return false
        }
    }
}