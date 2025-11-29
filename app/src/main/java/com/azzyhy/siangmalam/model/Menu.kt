package com.azzyhy.siangmalam.model

import android.util.Log

abstract class Menu(private val id: Int, private var nama: String, private val deskripsi:String, private var harga: Int, private var stok: Int, private var gambar: Int, private val tipe: String) {
    protected var hargaAwal: Int = this.harga

    fun ambilId(): Int {
        return this.id
    }
    fun ambilNama(): String {
        return this.nama
    }
    fun ambilHarga(): Int {
        return this.harga
    }
    fun ambilHargaAwal(): Int {
        return this.hargaAwal
    }
    fun ambilStok(): Int {
        return this.stok
    }
    fun ambilDeskripsi(): String {
        return this.deskripsi
    }
    fun ambilGambar(): Int {
        return this.gambar
    }
    fun updateHarga() {
        this.harga = hitungHarga()
    }
    fun kurangiStok(jumlahStok: Int) {
        if (jumlahStok > this.stok) {
            Log.e("Menu", "kurangi Stok: Jumlah stok yang diambil melebihi stok yang ada.", )
        } else {
            this.stok -= jumlahStok
        }
    }

    abstract fun hitungHarga(): Int
}
