package com.example.aplicacionemt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.work.*
import com.example.aplicacionemt.databinding.FragmentRecuerdameBinding
import com.example.aplicacionemt.db.AppDatabase
import com.example.aplicacionemt.db.Favorito
import com.example.aplicacionemt.worker.BusAlertWorker

class RecuerdameFragment : Fragment() {

    private var _binding: FragmentRecuerdameBinding? = null
    private val binding get() = _binding!!

    private var favoritos: List<Favorito> = emptyList()
    private lateinit var workManager: WorkManager

    // Descripción de la alerta activa (persiste mientras el Fragment existe)
    private var alertaDescripcion: String = ""

    // Solicita permiso POST_NOTIFICATIONS en Android 13+
    private val requestNotifPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            activarAlerta()
        } else {
            Toast.makeText(
                requireContext(),
                "Permiso de notificaciones necesario para que funcione la alerta",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecuerdameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workManager = WorkManager.getInstance(requireContext())

        setupSeekBar()
        loadFavoritos()
        observarEstadoAlerta()

        binding.btnActivar.setOnClickListener { checkPermisosYActivar() }
        binding.btnCancelar.setOnClickListener { cancelarAlerta() }
    }

    // ── SeekBar: 1..20 minutos ──────────────────────────────────────────────

    private fun setupSeekBar() {
        // max=19 → progress 0..19 → minutos 1..20
        binding.tvMinutos.text = "5 min"
        binding.seekBarMinutos.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
                val min = progress + 1
                binding.tvMinutos.text = "$min min"
            }
            override fun onStartTrackingTouch(sb: SeekBar) {}
            override fun onStopTrackingTouch(sb: SeekBar) {}
        })
    }

    // ── Cargar favoritos desde Room ─────────────────────────────────────────

    private fun loadFavoritos() {
        val dao = AppDatabase.getInstance(requireContext()).favoritoDao()
        dao.getAll().observe(viewLifecycleOwner) { lista ->
            favoritos = lista
            val sinFavoritos = lista.isEmpty()

            binding.spinnerFavoritos.visibility = if (sinFavoritos) View.GONE else View.VISIBLE
            binding.tvSinFavoritos.visibility = if (sinFavoritos) View.VISIBLE else View.GONE
            binding.btnActivar.isEnabled = !sinFavoritos

            if (!sinFavoritos) {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    lista.map { fav ->
                        val nombre = fav.alias.ifBlank { fav.nombreOficial }
                        "Parada ${fav.stopId} — $nombre"
                    }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerFavoritos.adapter = adapter
            }
        }
    }

    // ── Observar estado de WorkManager ─────────────────────────────────────

    private fun observarEstadoAlerta() {
        workManager
            .getWorkInfosForUniqueWorkLiveData(BusAlertWorker.WORK_NAME)
            .observe(viewLifecycleOwner) { infos ->
                val activa = infos.any {
                    it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING
                }
                binding.cardEstado.visibility = if (activa) View.VISIBLE else View.GONE
                binding.cardConfiguracion.isEnabled = !activa
                binding.btnActivar.isEnabled = !activa && favoritos.isNotEmpty()

                if (activa && alertaDescripcion.isNotEmpty()) {
                    binding.tvEstado.text = alertaDescripcion
                }
            }
    }

    // ── Activar alerta ─────────────────────────────────────────────────────

    private fun checkPermisosYActivar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permiso = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(requireContext(), permiso)
                == PackageManager.PERMISSION_GRANTED
            ) {
                activarAlerta()
            } else {
                requestNotifPermission.launch(permiso)
            }
        } else {
            activarAlerta()
        }
    }

    private fun activarAlerta() {
        val pos = binding.spinnerFavoritos.selectedItemPosition
        if (pos < 0 || pos >= favoritos.size) return

        val favorito = favoritos[pos]
        val lineId = binding.etLinea.text.toString().trim()
        val minutos = binding.seekBarMinutos.progress + 1
        val thresholdSeconds = minutos * 60

        val stopName = favorito.alias.ifBlank { favorito.nombreOficial }
        val lineaTxt = if (lineId.isBlank()) "cualquier bus" else "línea $lineId"
        alertaDescripcion = "Parada ${favorito.stopId} ($stopName)\n$lineaTxt · aviso con $minutos min de antelación"

        val data = workDataOf(
            BusAlertWorker.KEY_STOP_ID to favorito.stopId,
            BusAlertWorker.KEY_LINE_ID to lineId,
            BusAlertWorker.KEY_THRESHOLD_SECONDS to thresholdSeconds,
            BusAlertWorker.KEY_STOP_NAME to stopName
        )

        val request = OneTimeWorkRequestBuilder<BusAlertWorker>()
            .setInputData(data)
            .build()

        workManager.enqueueUniqueWork(
            BusAlertWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )

        binding.tvEstado.text = alertaDescripcion
        Toast.makeText(requireContext(), "✓ Alerta activada", Toast.LENGTH_SHORT).show()
    }

    // ── Cancelar alerta ────────────────────────────────────────────────────

    private fun cancelarAlerta() {
        workManager.cancelUniqueWork(BusAlertWorker.WORK_NAME)
        alertaDescripcion = ""
        Toast.makeText(requireContext(), "Alerta cancelada", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
