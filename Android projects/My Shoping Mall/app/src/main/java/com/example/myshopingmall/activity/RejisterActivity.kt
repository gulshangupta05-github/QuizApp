package com.example.myshopingmall.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myshopingmall.R
import com.example.myshopingmall.activity.fragmentSignIn_SgnUp.SignInFragment
import com.example.myshopingmall.activity.fragmentSignIn_SgnUp.SignUpFragment

class RejisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejister)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignUpFragment.newInstance())
                .commitNow()
        }

    }
}