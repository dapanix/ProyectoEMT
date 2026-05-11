package com.example.aplicacionemt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.aplicacionemt.db.AppDatabase
import com.example.aplicacionemt.db.Favorito
import com.example.aplicacionemt.model.InfoParadaResponse
import com.example.aplicacionemt.network.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class DetalleParadaViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).favoritoDao()

    private val _tiempos = MutableLiveData<InfoParadaResponse?>()
    val tiempos: LiveData<InfoParadaResponse?> = _tiempos

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _stopId = MutableLiveData<Int>()
    val esFavorito: LiveData<Boolean> = _stopId.switchMap { id ->
        dao.getByStopId(id).map { it != null }
    }

    fun init(stopId: Int) {
        if (_stopId.value != stopId) {
            _stopId.value = stopId
        }
    }

    fun cargarTiempos(stopId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _tiempos.value = fetchInfoParada(stopId)
            _isLoading.value = false
        }
    }

    fun guardarFavorito(stopId: Int, alias: String, nombreOficial: String) {
        viewModelScope.launch {
            val lineas = _tiempos.value?.infoPorLinea
                ?.map { it.linea }
                ?.distinct()
                ?.joinToString(",") ?: ""
            val efectivoAlias = if (alias.isBlank()) nombreOficial else alias
            dao.insert(Favorito(stopId, efectivoAlias, nombreOficial, lineas))
        }
    }

    fun eliminarFavorito(stopId: Int) {
        viewModelScope.launch {
            val favorito = dao.getByStopIdOnce(stopId) ?: return@launch
            dao.delete(favorito)
        }
    }

    private suspend fun fetchInfoParada(stopId: Int): InfoParadaResponse? =
        suspendCancellableCoroutine { cont ->
            val call = ApiClient.api.getInfoParada(stopId)
            cont.invokeOnCancellation { call.cancel() }
            call.enqueue(object : Callback<InfoParadaResponse> {
                override fun onResponse(
                    call: Call<InfoParadaResponse>,
                    response: Response<InfoParadaResponse>
                ) {
                    cont.resume(if (response.isSuccessful) response.body() else null)
                }
                override fun onFailure(call: Call<InfoParadaResponse>, t: Throwable) {
                    cont.resume(null)
                }
            })
        }
}
