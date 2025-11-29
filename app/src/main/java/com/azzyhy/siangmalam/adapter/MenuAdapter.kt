package com.azzyhy.siangmalam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azzyhy.siangmalam.databinding.ItemMenuBinding
import com.azzyhy.siangmalam.model.Menu
import com.azzyhy.siangmalam.utils.Formatting.toRupiah

class MenuAdapter(private val listMenu: ArrayList<Menu>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: Menu)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = listMenu[position]

        holder.binding.itemNama.text = menu.ambilNama()
        holder.binding.itemDeskripsi.text = menu.ambilDeskripsi()
        holder.binding.itemStok.text = "(Stok: ${menu.ambilStok()})"
        holder.binding.itemHarga.text = menu.ambilHarga().toRupiah()
        holder.binding.imgMenu.setImageResource(menu.ambilGambar())

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listMenu[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listMenu.size
}