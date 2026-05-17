package com.example.aplicacionemt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplicacionemt.model.ParadaMapa
import com.example.aplicacionemt.model.TodasParadasResponse
import com.example.aplicacionemt.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapaViewModel : ViewModel() {

    private val _todasParadas = MutableLiveData<List<ParadaMapa>>()
    val todasParadas: LiveData<List<ParadaMapa>> = _todasParadas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarParadas() {
        _isLoading.value = true
        _error.value = null
        ApiClient.api.getTodasParadas().enqueue(object : Callback<TodasParadasResponse> {
            override fun onResponse(
                call: Call<TodasParadasResponse>,
                response: Response<TodasParadasResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _todasParadas.value = response.body()?.paradas ?: emptyList()
                } else {
                    _error.value = "Error ${response.code()}: no se pudieron cargar las paradas"
                }
            }

            override fun onFailure(call: Call<TodasParadasResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Error de conexión: ${t.message}"
            }
        })
    }
}
