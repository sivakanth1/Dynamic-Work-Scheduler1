package com.example.dynamicworkscheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.dynamicworkscheduler.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
    }

    fun signIn(view: View) {
        val email = binding.emailLogin.text.toString()
        val password = binding.passwordLogin.text.toString()

        if(email.isEmpty()||email.startsWith(' ')){
            binding.emailLogin.error = "Enter valid mailId"
        }
        if(password.isEmpty()||password.startsWith(' ')||password.length<8){
            binding.passwordLogin.error = "Enter valid password"
        }
        else{
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Log In successful",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                }
                .addOnFailureListener {
                    if(it.message=="There is no user record corresponding to this identifier. The user may have been deleted."){
                        Toast.makeText(this,"No User Found,Please Sign Up..",Toast.LENGTH_SHORT).show()
                    }
                    if(it.message=="The password is invalid or the user does not have a password."){
                        Toast.makeText(this,"Invalid Password",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun openSignUpPage(view: View) {
        startActivity(Intent(this,CreateProfile::class.java))
        finish()
    }


}