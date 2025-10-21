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

// DashboardFragment displays a list of photography entities in a RecycleView
// It interacts with the DashboardViewModel to fetch data from a repository or API
// Users can click an entity to navigate to the DetailsFragment for more details

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!! // Safe non-null accessor for binding

    // ViewModel handles data fetching, loading state, and error handling
    private val viewModel: DashboardViewModel by viewModels()

    // Adapter for RecyclerView to display the list of entities
    private lateinit var adapter: EntitiesAdapter

    // Navigation argument received from LoginFragment
    private val args: DashboardFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the fragment layout and initialize binding object
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler() // Set up RecycleView with adapter and layout manager
        observeData() // Observe LiveData from ViewModel to update UI dynamically

        // Load dashboard data using the provided keypass from loginFragment
        viewModel.loadDashboard(args.keypass)
    }

    private fun setupRecycler() {
        // Initialize adapter and set clicker listener for each entity item
        adapter = EntitiesAdapter {
            // Navigate to DetailsFragment when an entity item is clicked
            val action = DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        // Assign layout manager and adapter to RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() {
        // Observe LiveData for entities list
        viewModel.entities.observe(viewLifecycleOwner) { result ->
            result.onSuccess { list ->
                // Update RecycleView with new data
                adapter.submitList(list)
                // Show empty state message if list is empty
                binding.emptyStateTextView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }.onFailure {
                // Show toast if there is an error fetching data
                Toast.makeText(context, "Error loading data: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
        // Observe loading state LiveData
        viewModel.isLoading.observe(viewLifecycleOwner) {
            // Show or hide progress bar during data fetching
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding to prevent memory leaks
    }
}


