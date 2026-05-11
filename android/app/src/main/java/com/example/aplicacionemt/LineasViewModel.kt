package com.example.aplicacionemt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionemt.model.LineaParadasResponse
import com.example.aplicacionemt.network.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class LineasViewModel : ViewModel() {

    private val _paradasLinea = MutableLiveData<LineaParadasResponse?>()
    val paradasLinea: LiveData<LineaParadasResponse?> = _paradasLinea

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun buscarParadasLinea(lineId: String) {
        if (lineId.isBlank()) return
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _paradasLinea.value = null

            val resultado = fetchParadasLinea(lineId.trim())
            _isLoading.value = false

            if (resultado == null || resultado.paradas.isEmpty()) {
                _error.value = "Línea no encontrada"
            } else {
                _paradasLinea.value = resultado
            }
        }
    }

    private suspend fun fetchParadasLinea(lineId: String): LineaParadasResponse? =
        suspendCancellableCoroutine { cont ->
            val call = ApiClient.api.getParadasLinea(lineId)
            cont.invokeOnCancellation { call.cancel() }
            call.enqueue(object : Callback<LineaParadasResponse> {
                override fun onResponse(
                    call: Call<LineaParadasResponse>,
                    response: Response<LineaParadasResponse>
                ) {
                    cont.resume(if (response.isSuccessful) response.body() else null)
                }
                override fun onFailure(call: Call<LineaParadasResponse>, t: Throwable) {
                    cont.resume(null)
                }
            })
        }
}
