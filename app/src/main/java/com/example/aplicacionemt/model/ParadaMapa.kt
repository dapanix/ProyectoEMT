package com.example.aplicacionemt.model

data class ParadaMapa(
    val idParada: Int,
    val nombre: String,
    val coordenadas: List<Double>,
    val lineas: List<String>
)

data class TodasParadasResponse(
    val total: Int,
    val paradas: List<ParadaMapa>
)
