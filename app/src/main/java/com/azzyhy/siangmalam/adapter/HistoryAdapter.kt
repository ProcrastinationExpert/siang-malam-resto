package com.azzyhy.siangmalam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azzyhy.siangmalam.databinding.ItemHistoryBinding
import com.azzyhy.siangmalam.model.Riwayat
import com.azzyhy.siangmalam.utils.Formatting.toRupiah

class HistoryAdapter(private val listRiwayat: ArrayList<Riwayat>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = listRiwayat[position]
        val satuTransaksi = riwayat.daftarPesanan

        holder.binding.judulTransaksi.text = "Transaksi #${position + 1}"
        holder.binding.tanggalPesanan.text = riwayat.tanggal

        holder.binding.totalHistory.text = riwayat.totalHarga.toRupiah()

        val jumlahItemBerbeda = satuTransaksi.size
        val totalJumlahItem = satuTransaksi.sumOf { it.jumlah }
        holder.binding.jumlahItem.text = "$jumlahItemBerbeda menu berbeda, $totalJumlahItem porsi"

        var pesananitemText = ""
        satuTransaksi.forEach { pesanan ->
            pesananitemText += "- ${pesanan.menu.ambilNama()} (x${pesanan.jumlah}) : ${pesanan.hitungSubtotal().toRupiah()}\n"
        }

        holder.binding.pesananItem.text = pesananitemText.trim()
    }

    override fun getItemCount(): Int = listRiwayat.size
}