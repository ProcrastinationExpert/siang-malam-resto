package com.azzyhy.siangmalam.model

import com.azzyhy.siangmalam.utils.Configuration

class Minuman(id: Int, nama: String, deskripsi:String, harga: Int, stok: Int, gambar: Int, private var isDingin: Boolean, tipe:String= "minuman"): Menu(id, nama, deskripsi , harga, stok, gambar, tipe) {
    fun cekDingin(): Boolean {
        return this.isDingin
    }

    fun toggleDingin() {
        this.isDingin = !this.isDingin
        updateHarga()
    }

    override fun hitungHarga(): Int {
        return if (this.isDingin) {
            this.hargaAwal + Configuration.hargaTambahanMinumanDingin
        } else {
            this.hargaAwal
        }
    }
}