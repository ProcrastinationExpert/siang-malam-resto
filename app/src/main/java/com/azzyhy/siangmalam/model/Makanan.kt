package com.azzyhy.siangmalam.model

import com.azzyhy.siangmalam.

class Makanan(id: Int, tipe:String, nama: String, harga: Double, stok: Int, kategori: String, private var levelPedas: Int): Menu(id, tipe, nama, harga, stok, kategori) {
    fun getLevelPedas(): Int {
        return this.levelPedas;
    }
    override fun hitungHarga(): Int {
        return 1;
    }
}