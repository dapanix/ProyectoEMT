package com.example.aplicacionemt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionemt.databinding.ItemParadaLineaBinding
import com.example.aplicacionemt.model.ParadaItem

class LineasAdapter(private val onClick: (ParadaItem) -> Unit) :
    RecyclerView.Adapter<LineasAdapter.ViewHolder>() {

    private var items: List<ParadaItem> = emptyList()

    fun submitList(list: List<ParadaItem>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemParadaLineaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParadaItem) {
            binding.tvNombreParada.text = item.nombre
            binding.tvIdParada.text = "Nº ${item.idParada}"
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemParadaLineaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
