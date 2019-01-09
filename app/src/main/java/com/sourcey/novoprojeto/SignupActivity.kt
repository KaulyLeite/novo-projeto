@file:Suppress("DEPRECATION")

package com.sourcey.novoprojeto

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import butterknife.BindView
import butterknife.ButterKnife

class SignupActivity : AppCompatActivity() {

    @BindView(R.id.input_name)
    lateinit var _nameText: EditText
    @BindView(R.id.input_email)
    lateinit var _emailText: EditText
    @BindView(R.id.input_password)
    lateinit var _passwordText: EditText
    @BindView(R.id.input_reEnterPassword)
    lateinit var _reEnterPasswordText: EditText
    @BindView(R.id.input_data)
    lateinit var _dataText: TextView
    @BindView(R.id.input_pais)
    lateinit var _paisText: TextView
    @BindView(R.id.input_estado)
    lateinit var _estadoText: TextView
    @BindView(R.id.input_address)
    lateinit var _addressText: EditText
    @BindView(R.id.input_mobile)
    lateinit var _mobileText: EditText
    @BindView(R.id.btn_signup)
    lateinit var _signupButton: Button
    @BindView(R.id.link_login)
    lateinit var _loginLink: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        ButterKnife.bind(this)

        _signupButton.setOnClickListener { signup() }

        _loginLink.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }

    fun signup() {
        Log.d(TAG, "Conta criada com sucesso!")

        if (!validate()) {
            onSignupFailed()
            return
        }

        _signupButton.isEnabled = false

        @Suppress("DEPRECATION") val progressDialog = ProgressDialog(this@SignupActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Criando conta...")
        progressDialog.show()

        android.os.Handler().postDelayed(
                {
                    onSignupSuccess()
                    progressDialog.dismiss()
                }, 3000)
    }

    fun onSignupSuccess() {
        _signupButton.isEnabled = true
        setResult(Activity.RESULT_OK, null)
        finish()
        Toast.makeText(baseContext, "Conta criada com sucesso!", Toast.LENGTH_LONG).show()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Falha na autenticação.", Toast.LENGTH_LONG).show()

        _signupButton.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true

        val name = _nameText.text.toString()
        val email = _emailText.text.toString()
        val password = _passwordText.text.toString()
        val reEnterPassword = _reEnterPasswordText.text.toString()
        val data = _dataText.text.toString()
        val pais = _paisText.text.toString()
        val estado = _estadoText.text.toString()
        val address = _addressText.text.toString()
        val mobile = _mobileText.text.toString()

        if (name.isEmpty() || name.length < 4) {
            _nameText.error = "Seu nome deve conter no mínimo 4 caracteres."
            valid = false
        } else {
            _nameText.error = null
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.error = "Insira um endereço de e-mail válido."
            valid = false
        } else {
            _emailText.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 16) {
            _passwordText.error = "Insira uma senha contendo de 4 à 16 caracteres."
            valid = false
        } else {
            _passwordText.error = null
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 16 || reEnterPassword != password) {
            _reEnterPasswordText.error = "A senha não confere com a anterior."
            valid = false
        } else {
            _reEnterPasswordText.error = null
        }

        if (data.isEmpty() || data.length != 10) {
            _dataText.error = "Insira uma data de nascimento válida."
            valid = false
        } else {
            _dataText.error = null
        }

        if (pais.isEmpty()) {
            _paisText.error = "Insira um país válido."
            valid = false
        } else {
            _paisText.error = null
        }

        if (estado.isEmpty()) {
            _estadoText.error = "Insira um estado válido."
            valid = false
        } else {
            _estadoText.error = null
        }

        if (address.isEmpty()) {
            _addressText.error = "Insira um endereço residencial válido."
            valid = false
        } else {
            _addressText.error = null
        }

        if (mobile.isEmpty() || mobile.length != 11) {
            _mobileText.error = "Insira um número de CPF válido."
            valid = false
        } else {
            _mobileText.error = null
        }

        return valid
    }

    companion object {
        private val TAG = "SignupActivity"
        val RESULT_OK = null
    }
}