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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            } else {
                Toast.makeText(context, "Please enter both fields", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { keypass ->
                val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment(keypass)
                findNavController().navigate(action)
            }.onFailure {
                Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.loginButton.isEnabled = !loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

