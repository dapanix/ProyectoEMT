package com.example.aplicacionemt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aplicacionemt.db.AppDatabase
import com.example.aplicacionemt.model.InfoParadaResponse
import com.example.aplicacionemt.model.ParadaConTiempos
import com.example.aplicacionemt.network.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class MisParadasViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).favoritoDao()

    private val _paradasConTiempos = MutableLiveData<List<ParadaConTiempos>>()
    val paradasConTiempos: LiveData<List<ParadaConTiempos>> = _paradasConTiempos

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun cargarTiempos() {
        viewModelScope.launch {
            _isLoading.value = true
            val favoritos = dao.getAllList()
            if (favoritos.isEmpty()) {
                _paradasConTiempos.value = emptyList()
                _isLoading.value = false
                return@launch
            }
            val resultados = favoritos.map { favorito ->
                ParadaConTiempos(favorito, fetchInfoParada(favorito.stopId))
            }
            _paradasConTiempos.value = resultados
            _isLoading.value = false
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
