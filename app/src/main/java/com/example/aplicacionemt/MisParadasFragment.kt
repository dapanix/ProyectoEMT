package com.example.aplicacionemt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionemt.databinding.FragmentMisParadasBinding

class MisParadasFragment : Fragment() {

    private var _binding: FragmentMisParadasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MisParadasViewModel by viewModels()
    private lateinit var adapter: ParadasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMisParadasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ParadasAdapter { favorito ->
            val intent = Intent(requireContext(), DetalleParadaActivity::class.java).apply {
                putExtra(DetalleParadaActivity.EXTRA_STOP_ID, favorito.stopId)
                putExtra(DetalleParadaActivity.EXTRA_STOP_NAME, favorito.alias)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.paradasConTiempos.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
            val isEmpty = lista.isEmpty()
            binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_mis_paradas, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_refresh -> {
                        viewModel.cargarTiempos()
                        true
                    }
                    R.id.action_buscar -> {
                        startActivity(Intent(requireContext(), BuscarParadaActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarTiempos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
