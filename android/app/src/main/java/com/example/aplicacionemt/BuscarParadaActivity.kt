package com.example.aplicacionemt

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionemt.databinding.ActivityBuscarParadaBinding
import com.example.aplicacionemt.model.BuscarParadaResponse
import com.example.aplicacionemt.model.InfoParadaResponse
import com.example.aplicacionemt.model.ParadaItem
import com.example.aplicacionemt.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuscarParadaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarParadaBinding

    private val adapter = BuscarParadaAdapter { parada ->
        val intent = Intent(this, DetalleParadaActivity::class.java).apply {
            putExtra(DetalleParadaActivity.EXTRA_STOP_ID, parada.idParada)
            putExtra(DetalleParadaActivity.EXTRA_STOP_NAME, parada.nombre)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarParadaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Buscar parada"

        binding.etNombre.hint = "Nombre o número de parada"

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnBuscar.setOnClickListener { buscar() }

        binding.etNombre.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                buscar()
                true
            } else false
        }
    }

    private fun buscar() {
        val texto = binding.etNombre.text.toString().trim()
        if (texto.isEmpty()) return

        ocultarTeclado()
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.tvSinResultados.visibility = View.GONE

        val stopIdNum = texto.toIntOrNull()
        if (stopIdNum != null) {
            buscarPorNumero(stopIdNum, texto)
        } else {
            buscarPorNombre(texto)
        }
    }

    private fun buscarPorNumero(stopId: Int, texto: String) {
        ApiClient.api.getInfoParada(stopId).enqueue(object : Callback<InfoParadaResponse> {
            override fun onResponse(
                call: Call<InfoParadaResponse>,
                response: Response<InfoParadaResponse>
            ) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val parada = ParadaItem(
                        idParada = stopId,
                        nombre = "Parada $texto",
                        coordenadas = emptyList()
                    )
                    adapter.submitList(listOf(parada))
                    binding.recyclerView.visibility = View.VISIBLE
                } else {
                    binding.tvSinResultados.text = "Parada no encontrada"
                    binding.tvSinResultados.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<InfoParadaResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                binding.tvSinResultados.text = "Parada no encontrada"
                binding.tvSinResultados.visibility = View.VISIBLE
            }
        })
    }

    private fun buscarPorNombre(texto: String) {
        ApiClient.api.buscarParada(texto).enqueue(object : Callback<BuscarParadaResponse> {
            override fun onResponse(
                call: Call<BuscarParadaResponse>,
                response: Response<BuscarParadaResponse>
            ) {
                binding.progressBar.visibility = View.GONE
                val paradas = if (response.isSuccessful) response.body()?.paradas else null
                if (paradas.isNullOrEmpty()) {
                    binding.tvSinResultados.text = "Sin resultados"
                    binding.tvSinResultados.visibility = View.VISIBLE
                } else {
                    adapter.submitList(paradas)
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<BuscarParadaResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                binding.tvSinResultados.text = "Error de conexión"
                binding.tvSinResultados.visibility = View.VISIBLE
            }
        })
    }

    private fun ocultarTeclado() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { imm.hideSoftInputFromWindow(it.windowToken, 0) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
