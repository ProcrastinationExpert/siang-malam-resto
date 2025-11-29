package com.azzyhy.siangmalam.utils

import java.text.NumberFormat
import java.util.Locale

object Formatting {
    fun Int.toRupiah(): String {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(this).replace("Rp", "Rp ").replace(",00","")
    }
}