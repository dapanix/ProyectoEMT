package com.example.aplicacionemt

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionemt.databinding.ActivityDetalleParadaBinding
import com.example.aplicacionemt.databinding.ItemDetalleLineaBinding

class DetalleParadaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STOP_ID = "STOP_ID"
        const val EXTRA_STOP_NAME = "STOP_NAME"
    }

    private lateinit var binding: ActivityDetalleParadaBinding
    private val viewModel: DetalleParadaViewModel by viewModels()

    private var stopId = 0
    private var stopName = ""
    private var menuItemStar: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleParadaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stopId = intent.getIntExtra(EXTRA_STOP_ID, 0)
        stopName = intent.getStringExtra(EXTRA_STOP_NAME) ?: ""

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = stopName

        binding.tvNumeroParada.text = "Nº $stopId"
        binding.tvNombreParada.text = stopName

        viewModel.init(stopId)

        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.tiempos.observe(this) { info ->
            binding.containerTiempos.removeAllViews()
            if (info == null || info.infoPorLinea.isEmpty()) {
                binding.tvSinDatos.visibility = View.VISIBLE
                return@observe
            }
            binding.tvSinDatos.visibility = View.GONE
            val inflater = LayoutInflater.from(this)
            info.infoPorLinea
                .groupBy { it.linea }
                .forEach { (linea, buses) ->
                    val row = ItemDetalleLineaBinding.inflate(
                        inflater, binding.containerTiempos, true
                    )
                    val isNight = linea.startsWith("N", ignoreCase = true)
                    row.tvBadge.text = linea
                    row.tvBadge.setBackgroundResource(
                        if (isNight) R.drawable.bg_badge_night else R.drawable.bg_badge_blue
                    )
                    row.tvBadge.setTextColor(
                        if (isNight) Color.parseColor("#FFD600") else Color.WHITE
                    )
                    row.tvDestino.text = buses.firstOrNull()?.destination ?: ""

                    buses.getOrNull(0)?.let { bus ->
                        row.tvTiempo1.text = formatTiempo(bus.estimateArrive)
                        row.tvTiempo1.setTextColor(colorForTiempo(bus.estimateArrive))
                    }
                    buses.getOrNull(1)?.let { bus ->
                        row.tvTiempo2.visibility = View.VISIBLE
                        row.tvTiempo2.text = formatTiempo(bus.estimateArrive)
                        row.tvTiempo2.setTextColor(colorForTiempo(bus.estimateArrive))
                    }
                }
        }

        viewModel.esFavorito.observe(this) { esFav ->
            menuItemStar?.setIcon(
                if (esFav) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )
        }

        viewModel.cargarTiempos(stopId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detalle_parada, menu)
        menuItemStar = menu.findItem(R.id.action_favorito)
        val esFav = viewModel.esFavorito.value ?: false
        menuItemStar?.setIcon(
            if (esFav) R.drawable.ic_star_filled else R.drawable.ic_star_outline
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.action_refresh -> { viewModel.cargarTiempos(stopId); true }
            R.id.action_favorito -> {
                if (viewModel.esFavorito.value == true) {
                    mostrarDialogoEliminar()
                } else {
                    mostrarDialogoGuardar()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mostrarDialogoGuardar() {
        val editText = EditText(this).apply { hint = "Apodo (opcional)" }
        val padding = (16 * resources.displayMetrics.density).toInt()
        val container = FrameLayout(this).apply {
            setPadding(padding, 0, padding, 0)
            addView(editText)
        }
        AlertDialog.Builder(this)
            .setTitle("Guardar como favorito")
            .setView(container)
            .setPositiveButton("Guardar") { _, _ ->
                viewModel.guardarFavorito(stopId, editText.text.toString(), stopName)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogoEliminar() {
        AlertDialog.Builder(this)
            .setTitle("¿Eliminar de favoritos?")
            .setPositiveButton("Sí") { _, _ -> viewModel.eliminarFavorito(stopId) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun formatTiempo(estimateArrive: Int): String = when {
        estimateArrive == 0 -> "Llegando"
        estimateArrive > 3600 -> "> 60 min"
        else -> "${estimateArrive / 60} min"
    }

    private fun colorForTiempo(estimateArrive: Int): Int = when {
        estimateArrive == 0 -> Color.parseColor("#2E7D32")
        estimateArrive > 3600 -> Color.parseColor("#757575")
        else -> Color.BLACK
    }
}
