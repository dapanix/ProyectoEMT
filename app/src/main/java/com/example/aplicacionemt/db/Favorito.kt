package com.example.aplicacionemt.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class Favorito(
    @PrimaryKey val stopId: Int,
    val alias: String,
    val nombreOficial: String,
    val lineas: String
)
