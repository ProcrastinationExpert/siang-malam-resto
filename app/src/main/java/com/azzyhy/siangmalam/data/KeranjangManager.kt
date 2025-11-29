package com.azzyhy.siangmalam.data // ⚠️ Sesuaikan dengan package kamu

import android.content.Context
import android.util.Log
import com.azzyhy.siangmalam.model.Pesanan

object KeranjangManager {

    private val listPesanan = ArrayList<Pesanan>()

    fun getListPesanan(): ArrayList<Pesanan> {
        return listPesanan
    }
    fun addItem(item: Pesanan) {
        listPesanan.add(item)
    }

    fun removeItemByMenuId(idMenu: Int) {
        listPesanan.forEach { pesanan -> if (pesanan.menu.ambilId() == idMenu) { listPesanan.remove(pesanan) } }
        Log.d("Keranjang", "Removing item succes")
    }

    fun hitungTotal(): Int {
        return listPesanan.sumOf { it.hitungSubtotal() }
    }

    fun cekBarangByMenuId(menuId: Int): Pesanan? {
        return listPesanan.find { it.menu.ambilId() == menuId }
    }

    fun isEmpty(): Boolean {
        return listPesanan.isEmpty()
    }

    fun checkout(context: Context): Boolean {
        if (!isEmpty()) {
            val transaksiBaru = ArrayList(listPesanan)
            RiwayatManager.simpanTransaksi(context, transaksiBaru)
            clear()
            return true
        } else {
            return false
        }
    }

    fun clear() {
        listPesanan.clear()
    }
}