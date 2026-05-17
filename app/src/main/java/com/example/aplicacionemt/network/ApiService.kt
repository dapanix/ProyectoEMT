package com.example.aplicacionemt.network

import com.example.aplicacionemt.model.BuscarParadaResponse
import com.example.aplicacionemt.model.InfoParadaResponse
import com.example.aplicacionemt.model.LineaParadasResponse
import com.example.aplicacionemt.model.TodasParadasResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("infoParada/{id}")
    fun getInfoParada(
        @Path("id") id: Int
    ): Call<InfoParadaResponse>

    @GET("buscarParada/{nombre}")
    fun buscarParada(
        @Path("nombre") nombre: String
    ): Call<BuscarParadaResponse>

    @GET("linea/{lineId}/paradas")
    fun getParadasLinea(
        @Path("lineId") lineId: String
    ): Call<LineaParadasResponse>

    @GET("paradas")
    fun getTodasParadas(): Call<TodasParadasResponse>
}
