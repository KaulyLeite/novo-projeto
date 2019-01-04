package com.sourcey.novoprojeto

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import butterknife.BindView
import butterknife.ButterKnife

class LoginActivity : AppCompatActivity() {

    @BindView(R.id.input_email)
    lateinit var _emailText: EditText
    @BindView(R.id.input_password)
    internal lateinit var _passwordText: EditText
    @BindView(R.id.btn_login)
    internal lateinit var _loginButton: Button
    @BindView(R.id.link_signup)
    internal lateinit var _signupLink: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        _loginButton.setOnClickListener { login() }

        _signupLink.setOnClickListener {
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    fun login() {
        Log.d(TAG, "Autenticação efetuada com sucesso!")

        if (!validate()) {
            onLoginFailed()
            return
        }

        _loginButton.isEnabled = false

        val progressDialog = ProgressDialog(this@LoginActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Autenticando...")
        progressDialog.show()

        val email = _emailText.text.toString()
        val password = _passwordText.text.toString()

        android.os.Handler().postDelayed(
                {
                    onLoginSuccess()
                    progressDialog.dismiss()
                }, 3000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == SignupActivity.RESULT_OK) {

                this.finish()
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    fun onLoginSuccess() {
        _loginButton.isEnabled = true
        finish()
        Toast.makeText(baseContext, "Autenticação efetuada com sucesso!", Toast.LENGTH_LONG).show()
    }

    fun onLoginFailed() {
        Toast.makeText(baseContext, "Falha na autenticação.", Toast.LENGTH_LONG).show()

        _loginButton.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true

        val email = _emailText.text.toString()
        val password = _passwordText.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.error = "Insira um endereço de e-mail válido."
            valid = false
        } else {
            _emailText.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText.error = "Insira uma senha válida."
            valid = false
        } else {
            _passwordText.error = null
        }

        return valid
    }

    companion object {
        private val TAG = "LoginActivity"
        private val REQUEST_SIGNUP = 0
    }
}