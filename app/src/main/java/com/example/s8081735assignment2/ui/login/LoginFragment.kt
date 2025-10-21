package com.example.s8081735assignment2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.s8081735assignment2.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

// Fragment that handles user login functionality
// Users enter their username and password to authenticate
//if successful, navigates to the dashboard fragment with keypass

@AndroidEntryPoint
class LoginFragment : Fragment() {

    // Binding object instance corresponding to the fragment_login.xml layout
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // ViewModel that manages login logic and API/network calls
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding and return the root view
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for login button
        binding.btnLogin.setOnClickListener {
            // Clear previous errors
            binding.tvError.text = ""
            binding.tvError.visibility = View.GONE

            // Retrieve input values and remove whitespace
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Validate that both fields are filled
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Call ViewModel login function to authenticate
                viewModel.login(username, password)
            } else {
                // Displays error if field is empty
                binding.tvError.text = "Please enter both fields"
                binding.tvError.visibility = View.VISIBLE
            }
        }

        // Observe login result LiveData from ViewModel
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { keypass ->
                // Login successful: clears errors and navigate to DashboardFragment
                binding.tvError.text = ""
                binding.tvError.visibility = View.GONE
                // Use navigation component to safely pass the keypass to DashboardFragment
                val action =
                    LoginFragmentDirections.actionLoginFragmentToDashboardFragment(keypass)
                findNavController().navigate(action)
            }.onFailure {
                // Login failed: display error message and show toast
                val msg = it.message ?: "Login failed. Please try again."
                binding.tvError.text = msg
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }

        // observe loading state to show/hide progress bar and disable button during API call
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.btnLogin.isEnabled = !loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}


