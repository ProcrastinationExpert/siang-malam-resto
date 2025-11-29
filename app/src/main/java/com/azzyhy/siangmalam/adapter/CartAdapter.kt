package com.azzyhy.siangmalam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.azzyhy.siangmalam.data.KeranjangManager
import com.azzyhy.siangmalam.databinding.ItemCartBinding
import com.azzyhy.siangmalam.model.Makanan
import com.azzyhy.siangmalam.model.Pesanan
import com.azzyhy.siangmalam.utils.Formatting.toRupiah
import com.azzyhy.siangmalam.model.Minuman
import com.azzyhy.siangmalam.utils.Configuration

class CartAdapter(private val listCart: ArrayList<Pesanan>, private val onCartChanged: () -> Unit) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pesanan = listCart[position] // Ini object Pesanan
        val menu = pesanan.menu // ambil menu dari pesanan

        holder.binding.itemNama.text = menu.ambilNama()
        holder.binding.itemImage.setImageResource(menu.ambilGambar())
        holder.binding.itemHarga.text = pesanan.hitungSubtotal().toRupiah()
        holder.binding.jumlahItem.text = pesanan.jumlah.toString()
        holder.binding.itemDeskripsi.text = "Detail:\nJumlah: ${pesanan.jumlah}\nHarga per item: ${menu.ambilHargaAwal().toRupiah()}"

        when (menu) {
            is Makanan -> {
                holder.binding.groupMakanan.visibility = View.VISIBLE
                holder.binding.groupMinuman.visibility = View.GONE
                holder.binding.levelPedas.text = menu.ambilLevelPedas().toString()

                if (menu.ambilLevelPedas() > 0) {
                    holder.binding.itemDeskripsi.text.let {
                        holder.binding.itemDeskripsi.text = it.toString() + "\nHarga pedas: ${Configuration.hargaTambahanMakananPedas.toRupiah()} * ${menu.ambilLevelPedas()} level"
                    }
                }

                holder.binding.btnTambahLevelPedas.setOnClickListener {
                    menu.tambahLevelPedas()
                    holder.binding.levelPedas.text = menu.ambilLevelPedas().toString()
                    notifyItemChanged(position)
                    onCartChanged()
                }

                holder.binding.btnKurangiLevelPedas.setOnClickListener {
                    menu.kurangiLevelPedas()
                    holder.binding.levelPedas.text = menu.ambilLevelPedas().toString()
                    notifyItemChanged(position)
                    onCartChanged()
                }
            }
            is Minuman -> {
                holder.binding.groupMakanan.visibility = View.GONE
                holder.binding.groupMinuman.visibility = View.VISIBLE
                holder.binding.cbDingin.isChecked = menu.cekDingin()

                if (menu.cekDingin()) {
                    holder.binding.itemDeskripsi.text.let {
                        holder.binding.itemDeskripsi.text = it.toString() + "\nHarga tambahan es: ${Configuration.hargaTambahanMinumanDingin.toRupiah()}"
                    }
                }

                holder.binding.cbDingin.setOnCheckedChangeListener { _, isChecked ->
                    if (menu.cekDingin() != isChecked) {
                        menu.toggleDingin()
                        notifyItemChanged(position)
                        onCartChanged()
                    }
                }
            }
        }

        holder.binding.btnTambahItem.setOnClickListener {
            if (pesanan.menu.ambilStok() > pesanan.jumlah) {
                pesanan.jumlah++
                notifyItemChanged(position)
                onCartChanged()
            } else {
                Toast.makeText(holder.itemView.context, "Jumlah item tidak boleh melebihi jumlah stok yang ada", Toast.LENGTH_SHORT).show()
                Log.e("CartAdapter", "onBindViewHolder: jumlah item yang ingin ditambah tidak boleh melebihi stok", )
            }
        }

        holder.binding.btnKurangiItem.setOnClickListener {
            if (pesanan.jumlah > 0) {
                pesanan.jumlah--
                if (pesanan.jumlah == 0) {
                    KeranjangManager.removeItemByMenuId(pesanan.menu.ambilId())
                    notifyItemRemoved(position)
                }
                notifyItemChanged(position)
                onCartChanged()
            } else {
                Log.e("CartAdapter", "onBindViewHolder: Pesanan harus lebih dari 0 untuk melakukan pengurangan item", )
            }
        }

        holder.binding.btnHapus.setOnClickListener {
            listCart.removeAt(position)
            notifyItemRemoved(position)
            onCartChanged()
        }
    }

    override fun getItemCount(): Int = listCart.size
}