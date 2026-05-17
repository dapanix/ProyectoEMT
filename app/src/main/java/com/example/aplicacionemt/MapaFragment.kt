package com.example.aplicacionemt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aplicacionemt.databinding.FragmentMapaBinding
import com.example.aplicacionemt.model.ParadaMapa
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager

class MapaFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapaViewModel by viewModels()
    private var googleMap: GoogleMap? = null
    private var clusterManager: ClusterManager<ParadaClusterItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = error
            } else {
                binding.tvError.visibility = View.GONE
            }
        }

        viewModel.todasParadas.observe(viewLifecycleOwner) { paradas ->
            if (paradas != null && googleMap != null) {
                mostrarParadasEnMapa(paradas)
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val madrid = LatLng(40.4168, -3.7038)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 12f))
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true

        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }

        val manager = ClusterManager<ParadaClusterItem>(requireContext(), map)
        clusterManager = manager
        map.setOnCameraIdleListener(manager)
        map.setOnMarkerClickListener(manager)

        manager.setOnClusterItemInfoWindowClickListener { item ->
            val intent = Intent(requireContext(), DetalleParadaActivity::class.java).apply {
                putExtra(DetalleParadaActivity.EXTRA_STOP_ID, item.stopId)
                putExtra(DetalleParadaActivity.EXTRA_STOP_NAME, item.getTitle())
            }
            startActivity(intent)
        }

        // Si los datos ya cargaron antes de que el mapa estuviera listo, mostrarlos ahora
        viewModel.todasParadas.value?.let { mostrarParadasEnMapa(it) }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.todasParadas.value.isNullOrEmpty()) {
            viewModel.cargarParadas()
        }
    }

    private fun mostrarParadasEnMapa(paradas: List<ParadaMapa>) {
        val manager = clusterManager ?: return
        manager.clearItems()

        for (parada in paradas) {
            val coords = parada.coordenadas
            if (coords.size < 2) continue
            val lon = coords[0]
            val lat = coords[1]
            // Descartar coordenadas nulas o claramente inválidas
            if (lat == 0.0 && lon == 0.0) continue
            if (lat < 35.0 || lat > 45.0 || lon < -10.0 || lon > 5.0) continue

            val snippet = parada.lineas.joinToString(", ")
            manager.addItem(ParadaClusterItem(lat, lon, parada.nombre, snippet, parada.idParada))
        }

        manager.cluster()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ParadaClusterItem(
    private val lat: Double,
    private val lon: Double,
    private val title: String,
    private val snippet: String,
    val stopId: Int
) : ClusterItem {
    override fun getPosition() = LatLng(lat, lon)
    override fun getTitle(): String = title
    override fun getSnippet(): String = snippet
    override fun getZIndex(): Float = 0f
}
