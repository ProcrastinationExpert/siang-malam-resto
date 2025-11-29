package com.azzyhy.siangmalam

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.res.ColorStateList
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.azzyhy.siangmalam.adapter.HeroSliderAdapter
import com.azzyhy.siangmalam.adapter.MenuAdapter
import com.azzyhy.siangmalam.data.DanaManager
import com.azzyhy.siangmalam.data.KeranjangManager
import com.azzyhy.siangmalam.data.RiwayatManager
import com.azzyhy.siangmalam.databinding.ActivityMainBinding
import com.azzyhy.siangmalam.model.Makanan
import com.azzyhy.siangmalam.model.Menu
import com.azzyhy.siangmalam.model.Pesanan
import com.azzyhy.siangmalam.model.Minuman
import com.azzyhy.siangmalam.utils.Configuration
import com.azzyhy.siangmalam.utils.Formatting.toRupiah
import com.google.android.material.button.MaterialButton

import com.azzyhy.siangmalam.data.DaftarMenu

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RiwayatManager.loadDataTransaction(this)

        setButtonActive(binding.btnSemua, true) // set button semua aktif awalnya

        val heroImages = listOf(
            R.drawable.ads1,
            R.drawable.ads2,
        )

        binding.heroSlider.adapter = HeroSliderAdapter(heroImages)
        binding.heroSlider.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val listMenuUtama = DaftarMenu.listMenu
        
        binding.listMenu.setHasFixedSize(true)
        binding.listMenu.layoutManager = GridLayoutManager(this, 2)

        updateDanaDompet()

        tampilkanDataKeRecyclerView(listMenuUtama)

        binding.btnSemua.setOnClickListener {
            changeFilterBtnColor("Semua")
            tampilkanDataKeRecyclerView(DaftarMenu.listMenu)
        }

        binding.btnMakanan.setOnClickListener {
            val listMakanan = ArrayList(DaftarMenu.listMenu.filter { it is Makanan })
            changeFilterBtnColor("Makanan")
            tampilkanDataKeRecyclerView(listMakanan)
        }

        binding.btnMinuman.setOnClickListener {
            val listMinuman = ArrayList(DaftarMenu.listMenu.filter { it is Minuman })
            changeFilterBtnColor("Minuman")
            tampilkanDataKeRecyclerView(listMinuman)
        }

        binding.ordersBtn.setOnClickListener { openCartPage() }
        binding.historyBtn.setOnClickListener { openHistoryPage() }

    }

    private fun tampilkanDataKeRecyclerView(data: ArrayList<Menu>) {
        val menuAdapter = MenuAdapter(data)
        binding.listMenu.adapter = menuAdapter

        menuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Menu) {
                addToCart(data)
            }
        })
    }

    private fun addToCart(menu: Menu) {
        val cekBarang = KeranjangManager.cekBarangByMenuId(menu.ambilId())

        if (cekBarang != null) {
            if (menu.ambilStok() > cekBarang.jumlah) {
                cekBarang.jumlah += 1
                cekBarang.subtotal = cekBarang.hitungSubtotal()
            }

            Log.d("MainActivity", "Barang ${menu.ambilNama()} sudah ada di keranjang. Jumlah sekarang: ${cekBarang.jumlah}")
            
            Toast.makeText(this, "Jumlah ${menu.ambilNama()} sekarang adalah ${cekBarang.jumlah}", Toast.LENGTH_SHORT).show()
        } else {
            val pesananBaru = Pesanan(
                menu = menu,
                jumlah = 1,
            )
            KeranjangManager.addItem(pesananBaru)
            Toast.makeText(this, "${menu.ambilNama()} masuk keranjang!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButtonActive(button: MaterialButton, active: Boolean) {

        val strokeWidth = (2 * resources.displayMetrics.density).toInt()

        if (active) {
            button.strokeWidth = 0
            button.strokeColor = ColorStateList.valueOf(Color.TRANSPARENT)
            button.backgroundTintList = ColorStateList.valueOf(Configuration.defaultButtonColor)
            button.setTextColor(Configuration.defaultFilterButtonTextColor)
        } else {
            button.strokeWidth = strokeWidth
            button.strokeColor = ColorStateList.valueOf(Configuration.defaultFilterStrokeColor)
            button.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
            button.setTextColor(Configuration.defaultFilterStrokeColor)
        }
    }

    private fun changeAllFilterButtonToUnclicked() {
        setButtonActive(binding.btnSemua, false)
        setButtonActive(binding.btnMakanan, false)
        setButtonActive(binding.btnMinuman, false)
    }

    private fun changeFilterBtnColor(tipe: String) {
        changeAllFilterButtonToUnclicked()

        when (tipe) {
            "Makanan" -> {
                setButtonActive(binding.btnMakanan, true)
            }
            "Minuman" -> {
                setButtonActive(binding.btnMinuman, true)
            }
            "Semua" -> {
                setButtonActive(binding.btnSemua, true)
            }
        }
    }

    private fun openCartPage() {
        if (KeranjangManager.isEmpty()) {
            Toast.makeText(this, "Keranjang kosong", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openHistoryPage() {
        if (RiwayatManager.isHistoryEmpty()) {
            Toast.makeText(this, "Riwayat transaksi kosong", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDanaDompet() {
        binding.danaDompet.text = DanaManager.saldo.toRupiah()
    }
}