package com.example.aplicacionemt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionemt.databinding.FragmentLineasBinding

class LineasFragment : Fragment() {

    private var _binding: FragmentLineasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LineasViewModel by viewModels()
    private val adapter = LineasAdapter { parada ->
        val intent = Intent(requireContext(), DetalleParadaActivity::class.java).apply {
            putExtra(DetalleParadaActivity.EXTRA_STOP_ID, parada.idParada)
            putExtra(DetalleParadaActivity.EXTRA_STOP_NAME, parada.nombre)
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLineasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.btnVerParadas.setOnClickListener {
            buscar()
        }

        binding.etLinea.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                buscar()
                true
            } else false
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.paradasLinea.observe(viewLifecycleOwner) { respuesta ->
            if (respuesta != null) {
                adapter.submitList(respuesta.paradas)
                binding.recyclerView.visibility = View.VISIBLE
                binding.tvError.visibility = View.GONE
            } else {
                binding.recyclerView.visibility = View.GONE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { msg ->
            if (msg != null) {
                binding.tvError.text = msg
                binding.tvError.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.GONE
            }
        }
    }

    private fun buscar() {
        val lineId = binding.etLinea.text.toString().trim()
        if (lineId.isNotEmpty()) {
            viewModel.buscarParadasLinea(lineId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
