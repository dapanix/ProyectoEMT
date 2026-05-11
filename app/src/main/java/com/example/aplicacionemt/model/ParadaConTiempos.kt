package com.example.aplicacionemt.model

import com.example.aplicacionemt.db.Favorito

data class ParadaConTiempos(
    val favorito: Favorito,
    val tiempos: InfoParadaResponse?
)
