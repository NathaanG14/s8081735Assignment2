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

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Login button logic
        binding.btnLogin.setOnClickListener {
            // Clear previous errors
            binding.tvError.text = ""
            binding.tvError.visibility = View.GONE

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            } else {
                binding.tvError.text = "Please enter both fields"
                binding.tvError.visibility = View.VISIBLE
            }
        }

        // Observe login result and show feedback
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { keypass ->
                // Clear any previous error message
                binding.tvError.text = ""
                binding.tvError.visibility = View.GONE
                // Navigate to dashboard
                val action =
                    LoginFragmentDirections.actionLoginFragmentToDashboardFragment(keypass)
                findNavController().navigate(action)
            }.onFailure {
                val msg = it.message ?: "Login failed. Please try again."
                binding.tvError.text = msg
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }

        // Show/hide loading spinner
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.btnLogin.isEnabled = !loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


