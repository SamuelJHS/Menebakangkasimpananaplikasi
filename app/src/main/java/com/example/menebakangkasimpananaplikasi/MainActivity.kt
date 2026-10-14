package com.example.menebakangkasimpananaplikasi

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var angkaRahasia = 0
    private var skor = 100
    private var percobaan = 0

    private lateinit var txtSkor: TextView
    private lateinit var txtPetunjuk: TextView
    private lateinit var inputTebakan: EditText
    private lateinit var btnTebak: Button
    private lateinit var btnReset: Button
    private lateinit var btnRefresh: ImageButton
    private lateinit var progressSkor: ProgressBar
    private lateinit var panelMain: View
    private lateinit var panelHasil: View
    private lateinit var iconHasil: TextView
    private lateinit var txtJudulHasil: TextView
    private lateinit var txtAngkaHasil: TextView
    private lateinit var txtSkorAkhir: TextView
    private lateinit var txtPercobaan: TextView
    private lateinit var txtRiwayatLabel: TextView
    private lateinit var containerRiwayat: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtSkor = findViewById(R.id.txtSkor)
        txtPetunjuk = findViewById(R.id.txtPetunjuk)
        inputTebakan = findViewById(R.id.inputTebakan)
        btnTebak = findViewById(R.id.btnTebak)
        btnReset = findViewById(R.id.btnReset)
        btnRefresh = findViewById(R.id.btnRefresh)
        progressSkor = findViewById(R.id.progressSkor)
        panelMain = findViewById(R.id.panelMain)
        panelHasil = findViewById(R.id.panelHasil)
        iconHasil = findViewById(R.id.iconHasil)
        txtJudulHasil = findViewById(R.id.txtJudulHasil)
        txtAngkaHasil = findViewById(R.id.txtAngkaHasil)
        txtSkorAkhir = findViewById(R.id.txtSkorAkhir)
        txtPercobaan = findViewById(R.id.txtPercobaan)
        txtRiwayatLabel = findViewById(R.id.txtRiwayatLabel)
        containerRiwayat = findViewById(R.id.containerRiwayat)

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

            percobaan++

            when {
                tebakanUser == angkaRahasia -> {
                    // Jika Benar: tampilkan panel hasil
                    tampilkanHasil(menang = true)
                }
                tebakanUser < angkaRahasia -> {
                    skor -= 10
                    tampilkanPetunjuk("↑  Terlalu kecil, coba lebih besar")
                    tambahRiwayat(tebakanUser, "↑")
                    cekSkorHabis()
                }
                tebakanUser > angkaRahasia -> {
                    skor -= 10
                    tampilkanPetunjuk("↓  Terlalu besar, coba lebih kecil")
                    tambahRiwayat(tebakanUser, "↓")
                    cekSkorHabis()
                }
            }

            updateSkor()
            inputTebakan.text.clear()
        }

        btnReset.setOnClickListener {
            mulaiGameBaru()
        }

        btnRefresh.setOnClickListener {
            mulaiGameBaru()
        }
    }

    private fun mulaiGameBaru() {
        angkaRahasia = Random.nextInt(0, 101)
        skor = 100
        percobaan = 0
        updateSkor()
        txtPetunjuk.text = "Tebak angka antara 0 sampai 100!"
        txtPetunjuk.setTextColor(Color.parseColor("#CCCCCC"))
        txtPetunjuk.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#141414"))
        inputTebakan.text.clear()
        containerRiwayat.removeAllViews()
        txtRiwayatLabel.visibility = View.GONE

        // Kembalikan tampilan ke kondisi awal bermain
        panelMain.visibility = View.VISIBLE
        panelHasil.visibility = View.GONE
    }

    private fun cekSkorHabis() {
        if (skor <= 0) {
            skor = 0
            updateSkor()
            tampilkanHasil(menang = false)
        }
    }

    private fun updateSkor() {
        txtSkor.text = "$skor"
        progressSkor.progress = skor
    }

    private fun tampilkanPetunjuk(teks: String) {
        txtPetunjuk.text = teks
        txtPetunjuk.setTextColor(Color.parseColor("#E8A317"))
        txtPetunjuk.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#3A2200"))
    }

    private fun tambahRiwayat(angka: Int, arah: String) {
        txtRiwayatLabel.visibility = View.VISIBLE
        val d = resources.displayMetrics.density
        val chip = TextView(this).apply {
            text = "$angka $arah"
            textSize = 15f
            setTextColor(Color.parseColor("#F08080"))
            setBackgroundResource(R.drawable.bg_chip)
            setPadding((16 * d).toInt(), (6 * d).toInt(), (16 * d).toInt(), (6 * d).toInt())
        }
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { marginEnd = (10 * d).toInt() }
        containerRiwayat.addView(chip, lp)
    }

    private fun tampilkanHasil(menang: Boolean) {
        if (menang) {
            iconHasil.text = "✓"
            iconHasil.setTextColor(Color.parseColor("#22B33A"))
            iconHasil.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#14301A"))
            txtJudulHasil.text = "Tebakanmu benar"
        } else {
            iconHasil.text = "✕"
            iconHasil.setTextColor(Color.parseColor("#E05555"))
            iconHasil.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#3D1414"))
            txtJudulHasil.text = "Game over! Skor habis"
        }
        txtAngkaHasil.text = "Angkanya adalah $angkaRahasia"
        txtSkorAkhir.text = "$skor"
        txtPercobaan.text = "$percobaan"

        panelMain.visibility = View.GONE
        panelHasil.visibility = View.VISIBLE
    }
}