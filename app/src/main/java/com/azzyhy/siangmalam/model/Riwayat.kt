package com.azzyhy.siangmalam.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Riwayat(
    val daftarPesanan: ArrayList<Pesanan>,
    val totalHarga: Int,
    val tanggal: String = SimpleDateFormat(
        "dd-MM-yyyy HH:mm:ss",
        Locale.getDefault()
    ).format(Date())
)