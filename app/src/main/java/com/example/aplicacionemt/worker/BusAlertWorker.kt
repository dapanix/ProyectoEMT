package com.example.aplicacionemt.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.aplicacionemt.R
import com.example.aplicacionemt.network.ApiClient
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Worker que vigila la parada cada 30 segundos y notifica cuando un bus llega al umbral.
 * Tras notificar, entra en modo WAITING_RESET: espera a que ese bus pase y el siguiente
 * aún no esté en rango. Cuando el siguiente entra en rango → nueva notificación.
 * El ciclo continúa hasta que el usuario cancele la alerta.
 *
 * Máquina de estados:
 *   WATCHING      → bus ≤ threshold → NOTIFICA → WAITING_RESET
 *   WAITING_RESET → bus > threshold (pasó, siguiente aún lejos) → WATCHING
 */
class BusAlertWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val KEY_STOP_ID            = "stop_id"
        const val KEY_LINE_ID            = "line_id"             // vacío = cualquier línea
        const val KEY_THRESHOLD_SECONDS  = "threshold_seconds"
        const val KEY_STOP_NAME          = "stop_name"
        /** true = ya se notificó, esperando a que el bus pase antes de vigilar el siguiente */
        private const val KEY_WAITING_RESET = "waiting_reset"

        const val WORK_NAME = "bus_alert"
        private const val CHANNEL_ID     = "bus_alert_channel"
        private const val NOTIF_ID       = 1001
        private const val POLL_DELAY_SEC = 30L
    }

    override fun doWork(): Result {
        val stopId           = inputData.getInt(KEY_STOP_ID, -1)
        val lineId           = inputData.getString(KEY_LINE_ID) ?: ""
        val thresholdSeconds = inputData.getInt(KEY_THRESHOLD_SECONDS, 300)
        val stopName         = inputData.getString(KEY_STOP_NAME) ?: "parada $stopId"
        val waitingReset     = inputData.getBoolean(KEY_WAITING_RESET, false)

        if (stopId == -1) return Result.failure()

        return try {
            val response = ApiClient.api.getInfoParada(stopId).execute()

            if (!response.isSuccessful) {
                reEnqueue(waitingReset)
                return Result.success()
            }

            val arrivals = response.body()?.infoPorLinea
            if (arrivals.isNullOrEmpty()) {
                reEnqueue(waitingReset)
                return Result.success()
            }

            // Bus más próximo (filtrado por línea si se indicó)
            val candidatos = if (lineId.isBlank()) arrivals
                             else arrivals.filter { it.linea.equals(lineId, ignoreCase = true) }

            val masCercano = candidatos.minByOrNull { it.estimateArrive }

            if (waitingReset) {
                // ── Estado WAITING_RESET ──────────────────────────────────────
                // Esperamos a que el bus notificado pase y el siguiente esté lejos.
                // Condición de salida: no hay bus O el más cercano ya supera el umbral.
                if (masCercano == null || masCercano.estimateArrive > thresholdSeconds) {
                    // El siguiente bus aún no está en rango → volver a WATCHING
                    reEnqueue(waitingReset = false)
                } else {
                    // El bus notificado sigue cerca (o llegó otro igual de rápido) → seguir esperando
                    reEnqueue(waitingReset = true)
                }
            } else {
                // ── Estado WATCHING ───────────────────────────────────────────
                if (masCercano != null && masCercano.estimateArrive <= thresholdSeconds) {
                    // ¡Bus en rango! → notificar
                    val minutos = masCercano.estimateArrive / 60
                    val segundos = masCercano.estimateArrive % 60
                    val tiempoTexto = when {
                        minutos <= 0 -> "ahora mismo"
                        segundos < 10 -> "en $minutos min"
                        else -> "en $minutos min"
                    }
                    val lineaTxt = if (lineId.isBlank()) "Bus ${masCercano.linea}" else "Línea ${masCercano.linea}"
                    val mensaje = "$lineaTxt → ${masCercano.destination} llega $tiempoTexto"
                    showNotification(stopName, mensaje)
                    // Cambiar a WAITING_RESET para esperar al siguiente bus
                    reEnqueue(waitingReset = true)
                } else {
                    // Bus aún lejos → seguir vigilando
                    reEnqueue(waitingReset = false)
                }
            }

            Result.success()

        } catch (e: IOException) {
            reEnqueue(waitingReset)
            Result.success()
        }
    }

    private fun reEnqueue(waitingReset: Boolean) {
        val nextData = Data.Builder()
            .putAll(inputData)
            .putBoolean(KEY_WAITING_RESET, waitingReset)
            .build()

        val next = OneTimeWorkRequestBuilder<BusAlertWorker>()
            .setInitialDelay(POLL_DELAY_SEC, TimeUnit.SECONDS)
            .setInputData(nextData)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, next)
    }

    private fun showNotification(stopName: String, mensaje: String) {
        val nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Alertas de bus",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Avisos cuando el autobús se acerca a tu parada" }
            nm.createNotificationChannel(channel)
        }

        val notif = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_directions_bus)
            .setContentTitle("🚌 NotifyMeBus — $stopName")
            .setContentText(mensaje)
            .setStyle(NotificationCompat.BigTextStyle().bigText(mensaje))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        nm.notify(NOTIF_ID, notif)
    }
}
