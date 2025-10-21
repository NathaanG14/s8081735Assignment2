package com.example.s8081735assignment2.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.s8081735assignment2.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

// Fragment that displays detailed information for a single photography entity
// This fragment is navigated to from the DashboardFragment when a user clicks on an item
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    // Binding object instance corresponding to the fragment_details.xml layout
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    // Navigation arguments to receive the selected entity object passed from the DashboardFragment
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout using View Binding and return the root view
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Extract the selected entity from the navigation arguments
        val e = args.entity

        // Binds entity details to UI elements
        // detailTechnique shows the photography technique used
        binding.detailTechnique.text = e.technique

        // detailDescription shows a textual description of the entity
        binding.detailDescription.text = e.description

        // The UI will display all necessary details for the selected entity
        // The fragment does not directly modify data; it only observes the passed entity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding to avoid memory leaks
    }
}
