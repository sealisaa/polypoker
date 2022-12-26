package com.example.polypoker.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.polypoker.MainActivity
import com.example.polypoker.R
import com.example.polypoker.model.User
import com.example.polypoker.retrofit.RetrofitService
import com.example.polypoker.retrofit.UserApi
import retrofit2.Call
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger
import javax.security.auth.callback.Callback

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.registration_fragment, container, false)
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

        val thisRegistrationFragment = this

        view.findViewById<Button>(R.id.register_button).setOnClickListener {
            val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
            val surnameEditText = view.findViewById<EditText>(R.id.surnameEditText)
            val loginEditText = view.findViewById<EditText>(R.id.loginEditText)
            val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
            val passwordAgainEditText = view.findViewById<EditText>(R.id.passwordAgainEditText)

            if (passwordEditText.text.toString().equals(passwordAgainEditText.text.toString())) {
                val user = User()
                user.name = nameEditText.text.toString()
                user.surname = surnameEditText.text.toString()
                user.login = loginEditText.text.toString()
                user.password = passwordEditText.text.toString()
                userApi.save(user).enqueue(object : retrofit2.Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                thisRegistrationFragment.context,
                                "Пользователь успешно зарегистрирован!",
                                Toast.LENGTH_LONG
                            ).show()
                            view.findNavController().navigate(
                                R.id.action_registrationFragment_to_logInFragment
                            )
                        }
                        else {
                            Toast.makeText(
                                thisRegistrationFragment.context,
                                "Ошибка регистрации",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(
                            thisRegistrationFragment.context,
                            "Ошибка регистрации",
                            Toast.LENGTH_LONG).show()
                        Logger.getLogger(newInstance()::class.java.name)
                            .log(Level.SEVERE, "Registration Error", t)
                    }

                })
            }
            else {
                Toast.makeText(
                    thisRegistrationFragment.context,
                    "Пароли не совпадают",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}