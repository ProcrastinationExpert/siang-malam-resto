package com.azzyhy.siangmalam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.azzyhy.siangmalam.adapter.HistoryAdapter
import com.azzyhy.siangmalam.data.RiwayatManager
import com.azzyhy.siangmalam.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnClear.setOnClickListener {
            RiwayatManager.clearHistory(this)
            Toast.makeText(this, "Riwayat transaksi dihapus", Toast.LENGTH_SHORT).show()
            setupRecyclerView()
        }

        binding.btnDashboard.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        if (RiwayatManager.isHistoryEmpty()) {
            binding.rvHistory.adapter = null
            Toast.makeText(this, "Riwayat transaksi kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = HistoryAdapter(RiwayatManager.listRiwayat)
        binding.rvHistory.adapter = adapter
    }
}