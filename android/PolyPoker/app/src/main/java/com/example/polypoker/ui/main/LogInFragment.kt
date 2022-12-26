package com.example.polypoker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.polypoker.R
import com.example.polypoker.model.User
import com.example.polypoker.retrofit.RetrofitService
import com.example.polypoker.retrofit.UserApi
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import java.util.logging.Level
import java.util.logging.Logger

class LogInFragment : Fragment() {
    companion object {
        fun newInstance() = LogInFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.log_in_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitService = RetrofitService()
        val userApi = retrofitService.retrofit.create(UserApi::class.java)

        view.findViewById<Button>(R.id.log_in_button).setOnClickListener {
            val loginEditText = view.findViewById<EditText>(R.id.loginEditText)
            val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)

            val login = loginEditText.text.toString()
            val password = passwordEditText.text.toString()

            val thisLogInFragment = this
            userApi.getUserByLogin(login).enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user?.password.equals(password)) {
                            Toast.makeText(
                                thisLogInFragment.context,
                                "Успешный вход!",
                                Toast.LENGTH_LONG
                            ).show()
                            view.findNavController().navigate(
                                R.id.action_logInFragment_to_mainMenuFragment
                            )
                        }
                        else {
                            Toast.makeText(
                                thisLogInFragment.context,
                                "Неверный пароль",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                    else {
                        Toast.makeText(
                            thisLogInFragment.context,
                            "Пользователя не существует",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        thisLogInFragment.context,
                        "Ошибка входа",
                        Toast.LENGTH_LONG).show()
                    Logger.getLogger(RegistrationFragment.newInstance()::class.java.name)
                        .log(Level.SEVERE, "Sign In Error", t)
                }

            })

        }

        view.findViewById<TextView>(R.id.registerText).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_logInFragment_to_registrationFragment
            )
        }
    }
}