package com.example.aplicacionemt.model

data class BuscarParadaResponse(
    val total: Int,
    val paradas: List<ParadaItem>
)

data class ParadaItem(
    val idParada: Int,
    val nombre: String,
    val coordenadas: List<Double>
)
