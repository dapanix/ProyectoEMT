package com.example.aplicacionemt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionemt.databinding.ItemResultadoBusquedaBinding
import com.example.aplicacionemt.model.ParadaItem

class BuscarParadaAdapter(private val onItemClick: (ParadaItem) -> Unit) :
    RecyclerView.Adapter<BuscarParadaAdapter.ViewHolder>() {

    private var items: List<ParadaItem> = emptyList()

    fun submitList(list: List<ParadaItem>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemResultadoBusquedaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParadaItem) {
            binding.tvNombre.text = item.nombre
            binding.tvNumero.text = "Nº ${item.idParada}"
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemResultadoBusquedaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
