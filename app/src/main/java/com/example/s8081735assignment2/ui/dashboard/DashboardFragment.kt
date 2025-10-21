package com.example.s8081735assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s8081735assignment2.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

// Fragment to display the dashboard of photography entities.
// Uses DashboardViewModel to fetch data and update UI.
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: EntitiesAdapter
    private val args: DashboardFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate fragment layout and initialize binding.
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler() // Initialize recycler view.
        observeData() // observe LiveData from ViewModel.
        viewModel.loadDashboard(args.keypass) // Trigger API call to load dashboard data.
    }

    private fun setupRecycler() {
        // Initialize adapter with click listener for each entity.
        adapter = EntitiesAdapter {
            // Navigate to DetailsFragment when an entity is clicked.
            val action = DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        // Set layout manager and adapter for RecycleView.
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() {
        // Observe entities LiveData from ViewModel.
        viewModel.entities.observe(viewLifecycleOwner) { result ->
            result.onSuccess { list ->
                adapter.submitList(list) // Update RecycleView
                // Show "empty state" text if list is empty.
                binding.emptyStateTextView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }.onFailure {
                // Display error message as toast if API call fails.
                Toast.makeText(context, "Error loading data: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }

        // Observe loading state to show/hide progress bar.
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}


