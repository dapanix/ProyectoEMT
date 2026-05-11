package com.example.aplicacionemt

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionemt.databinding.ItemLineaTiempoBinding
import com.example.aplicacionemt.databinding.ItemParadaFavoritaBinding
import com.example.aplicacionemt.db.Favorito
import com.example.aplicacionemt.model.ParadaConTiempos

class ParadasAdapter(private val onItemClick: (Favorito) -> Unit) :
    RecyclerView.Adapter<ParadasAdapter.ViewHolder>() {

    private var items: List<ParadaConTiempos> = emptyList()

    fun submitList(list: List<ParadaConTiempos>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemParadaFavoritaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParadaConTiempos) {
            binding.tvAlias.text = item.favorito.alias
            binding.tvNumeroParada.text = "Nº ${item.favorito.stopId}"
            val lineasFormateadas = item.favorito.lineas
                .split(",")
                .joinToString(" · ") { it.trim() }
            binding.tvLineas.text = "Líneas: $lineasFormateadas"

            binding.root.setOnClickListener { onItemClick(item.favorito) }

            binding.layoutTiempos.removeAllViews()
            val busList = item.tiempos?.infoPorLinea ?: emptyList()
            val inflater = LayoutInflater.from(binding.root.context)

            busList.take(4).forEach { lineaInfo ->
                val row = ItemLineaTiempoBinding.inflate(inflater, binding.layoutTiempos, true)
                val isNight = lineaInfo.linea.startsWith("N", ignoreCase = true)
                row.tvBadge.text = lineaInfo.linea
                row.tvBadge.setBackgroundResource(
                    if (isNight) R.drawable.bg_badge_night else R.drawable.bg_badge_blue
                )
                row.tvBadge.setTextColor(
                    if (isNight) Color.parseColor("#FFD600") else Color.WHITE
                )
                row.tvTiempo.text = when {
                    lineaInfo.estimateArrive == 0 -> "Llegando"
                    lineaInfo.estimateArrive > 3600 -> "> 60 min"
                    else -> "${lineaInfo.estimateArrive / 60} min"
                }
            }

            if (busList.size > 4) {
                val dots = TextView(binding.root.context).apply {
                    text = "..."
                    textSize = 13f
                    setTextColor(Color.parseColor("#757575"))
                    gravity = Gravity.CENTER
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                binding.layoutTiempos.addView(dots)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemParadaFavoritaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
