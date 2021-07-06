package com.example.myshopingmall.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myshopingmall.R
import com.example.myshopingmall.activity.fragmentSignIn_SgnUp.SignInFragment

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commitNow()
        }
    }
}