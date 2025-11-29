package com.azzyhy.siangmalam.data

import com.azzyhy.siangmalam.R
import com.azzyhy.siangmalam.model.Makanan
import com.azzyhy.siangmalam.model.Menu
import com.azzyhy.siangmalam.model.Minuman

object DaftarMenu {

    val listMenu = ArrayList<Menu>()

    init {
        listMenu.addAll(generateDummy())
    }

    fun getMenuById(id: Int): Menu? {
        return listMenu.find { it.ambilId() == id }
    }

    private fun generateDummy(): ArrayList<Menu> {
        val list = ArrayList<Menu>()

        // Makanan
        list.add(Makanan(1, "Nasi Rendang", "Nasi wuenak lah pokonya", 25000, 10, R.drawable.rendang, 0))
        list.add(Makanan(2, "Ayam Geprek", "Ayam geprek asli upnvj", 18000, 20, R.drawable.ayamgeprek, 1))
        list.add(Makanan(3, "Ayam Pop", "Ayam pop dari warpad asli", 12000, 30, R.drawable.ayampop, 2))

        // Minuman
        list.add(Minuman(1, "Es Teh Manis", "Es Teh Manis", 5000, 10, R.drawable.teh, true))
        list.add(Minuman(2, "Jus Jeruk", "Jus jeruk maknyus", 15000, 20, R.drawable.jusjeruk, false))
        list.add(Minuman(3, "Kopi hitam", "Kopi coy", 8000, 30, R.drawable.kopi, false))

        return list
    }
}
