package com.example.s8081735assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s8081735assignment2.R
import com.example.s8081735assignment2.util.Resource
import com.example.s8081735assignment2.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val vm: DashboardViewModel by viewModels()
    private lateinit var adapter: EntitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerEntities)
        val pb = view.findViewById<ProgressBar>(R.id.progressBarDashboard)
        val tvEmpty = view.findViewById<TextView>(R.id.tvEmpty)

        // Set up RecyclerView
        adapter = EntitiesAdapter(emptyList()) { entity ->
            val action = DashboardFragmentDirections
                .actionDashboardFragmentToDetailsFragment(entity)
            findNavController().navigate(action)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // Get argument keypass from login
        val keypass = DashboardFragmentArgs.fromBundle(requireArguments()).keypass
        vm.loadDashboardData(keypass)

        // Observe dashboard state
        lifecycleScope.launch {
            vm.dashboardData.collectLatest { state ->
                when (state) {
                    is Resource.Loading -> {
                        pb.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                        tvEmpty.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        pb.visibility = View.GONE
                        val data = state.data.entities ?: emptyList()
                        if (data.isEmpty()) {
                            tvEmpty.visibility = View.VISIBLE
                            recycler.visibility = View.GONE
                        } else {
                            adapter.setItems(data)
                            recycler.visibility = View.VISIBLE
                            tvEmpty.visibility = View.GONE
                        }
                    }
                    is Resource.Error -> {
                        pb.visibility = View.GONE
                        tvEmpty.text = state.message
                        tvEmpty.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                    }

                    Resource.Idle -> TODO()
                }
            }
        }
    }
}

