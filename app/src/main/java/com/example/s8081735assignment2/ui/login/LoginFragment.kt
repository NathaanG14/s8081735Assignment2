package com.example.s8081735assignment2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.s8081735assignment2.R
import com.example.s8081735assignment2.util.Resource
import com.example.s8081735assignment2.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val vm: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etUser = view.findViewById<EditText>(R.id.etUsername)
        val etPass = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val pb = view.findViewById<ProgressBar>(R.id.progressBar)
        val tvError = view.findViewById<TextView>(R.id.tvError)

        btnLogin.setOnClickListener {
            tvError.text = ""
            val username = etUser.text.toString().trim()
            val password = etPass.text.toString().trim()
            vm.loginUser(username, password)
        }

        etPass.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.performClick()
                true
            } else false
        }

        lifecycleScope.launch {
            vm.loginState.collectLatest { state ->
                when (state) {
                    is Resource.Loading -> {
                        pb.visibility = View.VISIBLE
                        btnLogin.isEnabled = false
                    }
                    is Resource.Success -> {
                        pb.visibility = View.GONE
                        btnLogin.isEnabled = true
                        val keypass = state.data.keypass ?: "photography"
                        val action =
                            LoginFragmentDirections.actionLoginFragmentToDashboardFragment(keypass)
                        findNavController().navigate(action)
                    }
                    is Resource.Error -> {
                        pb.visibility = View.GONE
                        btnLogin.isEnabled = true
                        tvError.text = state.message
                    }

                    Resource.Idle -> TODO()
                }
            }
        }
    }
}

