package com.example.menebakangkasimpananaplikasi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var angkaRahasia = 0
    private var skor = 100

    private lateinit var txtSkor: TextView
    private lateinit var txtPetunjuk: TextView
    private lateinit var inputTebakan: EditText
    private lateinit var btnTebak: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtSkor = findViewById(R.id.txtSkor)
        txtPetunjuk = findViewById(R.id.txtPetunjuk)
        inputTebakan = findViewById(R.id.inputTebakan)
        btnTebak = findViewById(R.id.btnTebak)
        btnReset = findViewById(R.id.btnReset)

        mulaiGameBaru()

        btnTebak.setOnClickListener {
            val teksInput = inputTebakan.text.toString().trim()

            if (teksInput.isEmpty()) {
                Toast.makeText(this, "Masukkan angka terlebih dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tebakanUser = teksInput.toInt()

            if (tebakanUser !in 0..100) {
                Toast.makeText(this, "Angka harus di antara 0 sampai 100!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when {
                tebakanUser == angkaRahasia -> {
                    // Jika Benar: Tampilkan pesan, sembunyikan tombol tebak, munculkan tombol reset
                    txtPetunjuk.text = "🎉 BENAR! Angka rahasianya adalah $angkaRahasia.\nSkor akhir kamu: $skor"
                    btnTebak.visibility = View.GONE
                    btnReset.visibility = View.VISIBLE
                }
                tebakanUser < angkaRahasia -> {
                    skor -= 10
                    txtPetunjuk.text = "⬆️ Terlalu KECIL! Coba angka yang lebih besar."
                    cekSkorHabis()
                }
                tebakanUser > angkaRahasia -> {
                    skor -= 10
                    txtPetunjuk.text = "⬇️ Terlalu BESAR! Coba angka yang lebih kecil."
                    cekSkorHabis()
                }
            }

            txtSkor.text = "Skor: $skor"
            inputTebakan.text.clear()
        }

        btnReset.setOnClickListener {
            mulaiGameBaru()
        }
    }

    private fun mulaiGameBaru() {
        angkaRahasia = Random.nextInt(0, 101)
        skor = 100
        txtSkor.text = "Skor: $skor"
        txtPetunjuk.text = "Tebak angka antara 0 sampai 100!"
        inputTebakan.text.clear()

        // Kembalikan tombol ke kondisi awal bermain
        btnTebak.visibility = View.VISIBLE
        btnReset.visibility = View.GONE
    }

    private fun cekSkorHabis() {
        if (skor <= 0) {
            skor = 0
            txtSkor.text = "Skor: $skor"
            txtPetunjuk.text = "❌ Game Over! Skor habis.\nAngka rahasianya adalah: $angkaRahasia"

            // Jika Skor Habis: Sembunyikan tombol tebak, munculkan tombol reset
            btnTebak.visibility = View.GONE
            btnReset.visibility = View.VISIBLE
        }
    }
}