package com.azzyhy.siangmalam // ⚠️ GANTI SESUAI PACKAGE KAMU

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.azzyhy.siangmalam.adapter.CartAdapter
import com.azzyhy.siangmalam.data.DaftarMenu
import com.azzyhy.siangmalam.data.DanaManager
import com.azzyhy.siangmalam.data.KeranjangManager
import com.azzyhy.siangmalam.databinding.ActivityCartBinding
import com.azzyhy.siangmalam.utils.Formatting.toRupiah

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCart.setHasFixedSize(true)
        binding.rvCart.layoutManager = LinearLayoutManager(this)

        updateTampilanTotal()

        val cartAdapter = CartAdapter(KeranjangManager.getListPesanan()) {
            updateTampilanTotal()
        }

        binding.rvCart.adapter = cartAdapter

        binding.btnBayar.setOnClickListener {
            if (KeranjangManager.isEmpty()) {
                Toast.makeText(this, "Keranjang kosong", Toast.LENGTH_SHORT).show()
            } else {
                prosesBayar()
            }
        }

        binding.btnDashboard.setOnClickListener {
            finish()
        }

    }

    private fun updateTampilanTotal() {
        val total = KeranjangManager.hitungTotal()
        binding.itemTotalBayar.text = total.toRupiah()
    }

    private fun prosesBayar() {
        if (!DanaManager.cekSanggup(KeranjangManager.hitungTotal())) {
            Toast.makeText(this, "Pembayaran gagal, saldo tidak cukup", Toast.LENGTH_LONG).show()
            return
        }

        KeranjangManager.getListPesanan().forEach { pesanan ->
            val menuDiDaftar = DaftarMenu.getMenuById(pesanan.menu.ambilId())
            menuDiDaftar?.kurangiStok(pesanan.jumlah)
        }

        DanaManager.ambilSaldo(KeranjangManager.hitungTotal())

        val status = KeranjangManager.checkout(this)

        if (status) {
            Toast.makeText(this, "Pembayaran berhasil", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Terjadi kesalahan saat checkout", Toast.LENGTH_LONG).show()
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}