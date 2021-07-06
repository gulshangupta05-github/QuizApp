package com.example.myshopingmall

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.example.myshopingmall.activity.MainActivity
import com.example.myshopingmall.activity.RejisterActivity
import com.example.myshopingmall.activity.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    var firebaseAuth :FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        firebaseAuth= FirebaseAuth.getInstance()

        SystemClock.sleep(3000)


    }


    override fun onStart() {
        super.onStart()
        initialise()
    }

    private fun initialise() {


        val currentUser = firebaseAuth!!.currentUser
        if (currentUser == null){
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            this.finish()
        }else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
//    private fun initialise() {
//        try {
//            if (FirebaseAuth.getInstance().currentUser != null){
//                loggedInState()
//            } else {
//                loggedOutState()
//            }
//        }catch (e:Exception){
//            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun loggedInState() {
//        startActivity(Intent(this, SignInActivity::class.java))
//        this.finish()
//    }
//
//    private fun loggedOutState() {
//        startActivity(Intent(this, MainActivity::class.java))
//        this.finish()
//    }
}