package com.azzyhy.siangmalam.model

data class Pesanan(
    val menuId: Int,
    val namaMenu: String,
    val hargaSatuan: Double,
    var jumlah: Int
) {
    var subtotal: Double = hargaSatuan * jumlah

    fun displayInfo() {
        println("$namaMenu x$jumlah ($subtotal)")
    }
}