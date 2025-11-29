package com.azzyhy.siangmalam.data

import com.azzyhy.siangmalam.utils.Configuration

object DanaManager {
    var saldo: Int = Configuration.defaultStartMoney

    fun cekSanggup(jumlahSaldoHarusDibayar: Int): Boolean {
        return saldo >= jumlahSaldoHarusDibayar
    }

    fun ambilSaldo(jumlahSaldoYangDibutuhkan: Int) {
        saldo -= jumlahSaldoYangDibutuhkan
    }
}