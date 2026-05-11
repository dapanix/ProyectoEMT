package com.example.aplicacionemt.model

data class LineaParadasResponse(
    val linea: String,
    val total: Int,
    val paradas: List<ParadaItem>
)
