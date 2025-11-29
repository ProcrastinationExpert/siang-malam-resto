package com.azzyhy.siangmalam.model

import com.azzyhy.siangmalam.utils.Configuration.hargaTambahanMakananPedas

class Makanan(id: Int, nama: String, deskripsi:String, harga: Int, stok: Int, gambar: Int, private var levelPedas: Int =0, tipe: String="makanan"): Menu(id, nama, deskripsi, harga, stok, gambar, tipe) {
    fun ambilLevelPedas(): Int {
        return this.levelPedas
    }

    fun tambahLevelPedas() {
        if (this.levelPedas < 8) {
            this.levelPedas += 1
        }
        updateHarga()
    }

    fun kurangiLevelPedas() {
        if (this.levelPedas > 0) {
            this.levelPedas -= 1
        }
        updateHarga()
    }

    override fun hitungHarga(): Int {
        return this.hargaAwal + (this.levelPedas * hargaTambahanMakananPedas)
    }
}