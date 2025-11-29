package com.azzyhy.siangmalam.model

data class Pesanan(
    val menu: Menu,
    var jumlah: Int,
    var subtotal: Int = 0,
) {
    fun hitungSubtotal(): Int {
        this.subtotal = menu.ambilHarga() * this.jumlah
        return subtotal
    }
}