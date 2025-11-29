package com.azzyhy.siangmalam.data

import com.azzyhy.siangmalam.model.Pesanan

import android.content.Context
import com.azzyhy.siangmalam.model.Riwayat
import com.azzyhy.siangmalam.utils.DataConverter

object RiwayatManager {
    var listRiwayat = ArrayList<Riwayat>()

    fun simpanTransaksi(context: Context, transaksiBaru: ArrayList<Pesanan>) {
        val total = transaksiBaru.sumOf { it.hitungSubtotal() }
        val riwayatBaru = Riwayat(transaksiBaru, total)
        listRiwayat.add(riwayatBaru)
        DataConverter.saveTransactions(context, listRiwayat)
    }

    fun isHistoryEmpty(): Boolean {
        return listRiwayat.isEmpty()
    }

    fun clearHistory(context: Context) {
        listRiwayat.clear()
        DataConverter.clearAllTransactions(context)
    }

    fun loadDataTransaction(context: Context) {
        listRiwayat = DataConverter.loadTransactions(context)
    }
}