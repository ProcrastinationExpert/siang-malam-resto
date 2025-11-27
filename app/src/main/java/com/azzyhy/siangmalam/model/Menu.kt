package com.azzyhy.siangmalam.model

abstract class Menu(private val id: Int, private val tipe:String, private var nama: String, private var harga: Double, private var stok: Int, private var kategori: String) {
    fun ambilId(): Int {
        return this.id
    }
    fun ambilNama(): String {
        return this.nama
    }
    fun ambilHarga(): Double {
        return this.harga
    }
    fun ambilStok(): Int {
        return this.stok
    }
    fun ambilTipe(): String {
        return this.tipe
    }
    fun kurangiStok(jumlahStok: Int) {
        if (jumlahStok > this.stok) {
            error("Jumlah stok yang diambil melebihi stok yang ada")
        } else {
            this.stok -= jumlahStok
        }
    }
    abstract fun hitungHarga(): Int
    open fun getKategori(): String {
        return "Umum"
    }
}
