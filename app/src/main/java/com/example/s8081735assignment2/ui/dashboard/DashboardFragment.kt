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

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, s: Bundle?): View? =
        inflater.inflate(R.layout.fragment_dashboard, c, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerEntities)
        val pb = view.findViewById<ProgressBar>(R.id.progressBarDashboard)
        val tvEmpty = view.findViewById<TextView>(R.id.tvEmpty)

        adapter = EntitiesAdapter(emptyList()) { entity ->
            // navigate to details with parcelable entity using SafeArgs
            val action = DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(entity)
            findNavController().navigate(action)
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // read argument keypass
        val keypass = DashboardFragmentArgs.fromBundle(requireArguments()).keypass
        vm.loadEntities(keypass)

        lifecycleScope.launch {
            vm.entitiesState.collectLatest { state ->
                when (state) {
                    is Resource.Loading -> {
                        pb.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                        tvEmpty.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        pb.visibility = View.GONE
                        val list = state.data
                        if (list.isEmpty()) {
                            tvEmpty.visibility = View.VISIBLE
                            recycler.visibility = View.GONE
                        } else {
                            adapter.setItems(list)
                            recycler.visibility = View.VISIBLE
                            tvEmpty.visibility = View.GONE
                        }
                    }
                    is Resource.Error -> {
                        pb.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                        tvEmpty.text = state.message
                        recycler.visibility = View.GONE
                    }
                }
            }
        }
    }
}
