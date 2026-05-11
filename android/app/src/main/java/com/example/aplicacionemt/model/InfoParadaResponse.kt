package com.example.aplicacionemt.model

data class InfoParadaResponse(
    val parada: Int,
    val infoPorLinea: List<LineaInfo>
)

data class LineaInfo(
    val linea: String,
    val estimateArrive: Int,
    val destination: String
)
